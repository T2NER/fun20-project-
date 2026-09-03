# Monetization status

PlayFun20 now includes Google Mobile Ads SDK and Google UMP consent handling in the shared Android runtime used by all 20 product flavors.

Current implementation:

- Google Mobile Ads SDK is integrated.
- Google UMP consent information is refreshed at app launch before ads are requested when consent is required.
- Each flavor has its own AdMob app-ID property and banner-ID property.
- Normal regression builds intentionally fall back to Google's official test IDs so automated tests never generate production ad traffic.
- Production mode (`PLAYFUN20_PRODUCTION=true`) rejects missing IDs and rejects Google's test IDs.
- Production release signing is separately gated and verified before AAB artifacts are published.

Production AdMob mapping is defined by `ADMOB_PROPERTIES.template`. Create one AdMob app and at least one banner ad unit for each package, fill all 40 values, base64-encode the completed properties file, and store it in the GitHub Actions secret `ADMOB_PROPERTIES_B64`.

Revenue path is: installed app -> Google Mobile Ads/AdMob -> AdMob earnings/payments profile -> verified payment method. Bank/payment credentials must be entered only inside Google's secure payments interface and must never be committed to this repository.

Before public release, revalidate the privacy policy and Google Play Data safety declarations against the exact monetized binary and the disclosures presented by the Google Mobile Ads/UMP SDK versions in use.
