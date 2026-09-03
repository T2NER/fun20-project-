# PlayFun20 Store Metadata

The canonical store-listing source, privacy/data-safety drafts, and content-rating notes for all 20 apps are retained in the PlayFun20 project source package. This repository is the active build/regression/release workspace. Before production, each listing must be revalidated against the exact signed monetized binary and real screenshots.

Apps covered: TruthTap, Roast Button, BS Meter, Fake Call Fun, Most Likely Party, DareDrop, Settle It, Screen Crack Prank, VoiceWarp Lite, PrankDeck, AgeGuess Fun, FaceScore Fun, BarkBack, BabbleBack, Spooky Radar, SpinDecide, AskOrb, EscapeCall, TapReflex, and PocketGuard Alarm.

Current production assumptions:
- No user account and no developer-operated backend are required by the shared base app architecture.
- Google Mobile Ads SDK and Google UMP are present in all 20 monetized binaries and must be reflected in privacy policy/Data safety/ads declarations.
- Microphone permission remains limited to VoiceWarp Lite, BarkBack, and BabbleBack.
- Selected photos for AgeGuess Fun and FaceScore Fun are intended for local processing by the app; any SDK-level collection/disclosure must still be declared according to the exact final dependency behavior.
- Sensor readings used by Spooky Radar and PocketGuard Alarm are intended to remain local-only.
- Production builds must use the gated signed release workflow and real per-app AdMob IDs; regression binaries using Google test IDs are not production artifacts.

Before Play submission, re-check every Play Console declaration against the final AAB, Google Play's current Data safety definitions, the Google Mobile Ads/UMP data-disclosure documentation, and any app-specific permission or content changes made after this file was updated.
