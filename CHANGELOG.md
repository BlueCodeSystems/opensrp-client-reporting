# Changelog

## [0.3.0] - 2025-09-26
### Features
- Localize pagination status messaging across English, French, and Swahili resources for reporting UIs.

### Documentation
- Restore JitPack badge automation details and expand README coverage with overview, install, usage, and contributing guidance.

## [0.2.0] - 2025-09-25
### Bug Fixes
- Adapt SnakeYAML constructor usage to the new LoaderOptions API to restore release builds.

### Build & CI
- Upgrade the toolchain to Gradle 8.7 and Android Gradle Plugin 8.6.0.
- Add a GitHub Actions workflow that refreshes JitPack badges and primes master snapshots.

### Testing
- Replace legacy PowerMock patterns with Mockito/Robolectric friendly tests and supporting helpers.

### Documentation
- Document the managed JitPack badge block in the README for clearer status tracking.
