# Release signing

Google Play production uploads must be signed. Keep the upload keystore outside the repository. Configure signing locally or in GitHub Actions using encrypted secrets such as `UPLOAD_KEYSTORE_B64`, `UPLOAD_STORE_PASSWORD`, `UPLOAD_KEY_ALIAS`, and `UPLOAD_KEY_PASSWORD`.

Regression builds are unsigned until those secrets are deliberately configured.
