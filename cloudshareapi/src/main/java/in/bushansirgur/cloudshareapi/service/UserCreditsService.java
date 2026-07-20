package in.bushansirgur.cloudshareapi.service;

import in.bushansirgur.cloudshareapi.document.UserCredits;
import in.bushansirgur.cloudshareapi.repository.UserCreditsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreditsService {

    private final UserCreditsRepository userCreditsRepository;
    private final ProfileService profileService;
    private final RedisCreditsService redisCreditsService;

    public UserCredits createInitialCredits(String clerkId) {
        UserCredits userCredits = UserCredits.builder()
                .clerkId(clerkId)
                .credits(5)
                .plan("BASIC")
                .build();
        userCredits = userCreditsRepository.save(userCredits);
        redisCreditsService.initializeCredits(clerkId, 5);
        return userCredits;
    }

    public UserCredits getUserCredits(String clerkId) {
        return userCreditsRepository.findByClerkId(clerkId)
                .orElseGet(() -> createInitialCredits(clerkId));
    }

    public UserCredits getUserCredits() {
        String clerkId = profileService.getCurrentProfile().getClerkId();
        return getUserCredits(clerkId);
    }

    public Boolean hasEnoughCredits(int requiredCredits) {
        UserCredits userCredits = getUserCredits();
        return userCredits.getCredits() >= requiredCredits;
    }

    /**
     * Atomically consumes one credit. Redis performs the actual atomic
     * decrement (preventing race conditions when concurrent requests happen
     * at the same time); MongoDB is then updated to match, since Mongo stays
     * the durable, source-of-truth record for display elsewhere in the app.
     * Returns null if there weren't enough credits (nothing is deducted).
     */
    public UserCredits consumeCredit() {
        String clerkId = profileService.getCurrentProfile().getClerkId();

        UserCredits userCredits = getUserCredits(clerkId);
        // Safety net: backfills Redis for users who existed before Redis was
        // introduced, or if Redis was ever flushed/restarted without persistence.
        redisCreditsService.initializeCreditsIfAbsent(clerkId, userCredits.getCredits());

        boolean consumed = redisCreditsService.tryConsumeCredit(clerkId);
        if (!consumed) {
            return null;
        }

        userCredits.setCredits(userCredits.getCredits() - 1);
        return userCreditsRepository.save(userCredits);
    }

    public UserCredits addCredits(String clerkId, Integer creditsToAdd, String plan) {
        UserCredits userCredits = userCreditsRepository.findByClerkId(clerkId)
                .orElseGet(() -> createInitialCredits(clerkId));

        userCredits.setCredits(userCredits.getCredits() + creditsToAdd);
        userCredits.setPlan(plan);
        userCredits = userCreditsRepository.save(userCredits);

        redisCreditsService.addCredits(clerkId, creditsToAdd);
        return userCredits;
    }
}
