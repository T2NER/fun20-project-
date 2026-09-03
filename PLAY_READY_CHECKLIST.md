# Google Play production readiness

## Repository/build layer complete
- targetSdk 36 / compileSdk 36.
- Unique application ID for every app.
- Android App Bundle targets for all 20 apps.
- Google Mobile Ads SDK integrated.
- Google UMP consent flow integrated before ad requests when consent is required.
- Separate AdMob app-ID and banner-ID properties for every flavor.
- Regression builds use Google test IDs only.
- Production mode rejects missing/test AdMob IDs.
- AndroidX enabled for current Google Mobile Ads dependencies.
- Production signing configuration added without committing keystore credentials.
- Manual production workflow requires all signing/AdMob inputs, builds exactly 20 release AABs, verifies all signatures, and uploads the signed AAB artifact.
- Full monetized regression baseline passed: 20 debug APKs + 20 release AABs + lint + 20/20 emulator smoke tests.

## External account/release gates still required
1. In AdMob, create/link the 20 apps and create the 20 production banner ad units. Fill `ADMOB_PROPERTIES.template`, encode it, and store it as the repository secret `ADMOB_PROPERTIES_B64`.
2. Create/secure the Google Play upload key and store the four signing inputs as repository secrets described in `RELEASE_SIGNING.md`.
3. Run `Production Release - 20 Apps`. Treat the resulting `PlayFun20-production-signed-aabs` artifact as the only production upload set.
4. Capture real final-device screenshots for all listings.
5. Host public privacy-policy URLs and revalidate the policies/Data safety declarations for Google Mobile Ads + UMP and each app's actual permissions/data behavior.
6. Complete content-rating, ads declaration, target-audience, app-access, and other Play Console forms for each listing.
7. Run internal testing on physical Android devices and resolve Play pre-launch-report findings.
8. Complete any closed-testing requirement that applies to the Google Play developer account.
9. Upload each signed AAB to its matching package/listing and complete Google review/release steps.
10. In AdMob/Google Payments, complete identity/tax/payment-profile requirements and add/verify the payout method. Payment/bank credentials must only be entered in Google's secure interface.

The project is not considered commercially complete until the external gates above are confirmed. A green GitHub regression proves the binaries work; it cannot by itself prove Play approval, live ad serving, or payout-account verification.
