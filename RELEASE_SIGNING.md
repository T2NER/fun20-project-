# Release signing

Google Play production uploads must be signed. Keep the upload keystore outside the repository and never commit passwords or private keys.

The repository now has two deliberately different paths:

- `.github/workflows/regression.yml` builds test/regression binaries. It may use Google test AdMob IDs and does not require production signing secrets.
- `.github/workflows/production-release.yml` is manual-only and refuses to build unless production monetization and signing inputs are present.

Required GitHub Actions secrets for the production workflow:

- `UPLOAD_KEYSTORE_B64` — base64 of the upload `.jks`/`.keystore` file.
- `UPLOAD_STORE_PASSWORD` — upload keystore password.
- `UPLOAD_KEY_ALIAS` — upload key alias.
- `UPLOAD_KEY_PASSWORD` — upload key password.
- `ADMOB_PROPERTIES_B64` — base64 of a completed `ADMOB_PROPERTIES.template` containing all 20 AdMob app IDs and all 20 banner IDs.

When `PLAYFUN20_PRODUCTION=true`, `app/build.gradle` fails configuration if signing values are absent or if any AdMob value is missing or still equals Google's official test ID. The production workflow then builds exactly 20 release AABs and verifies every AAB signature with `jarsigner -verify -strict` before publishing the artifact.

The upload keystore and AdMob properties are materialized only on the ephemeral GitHub runner and are not committed to the repository.
