# Rowan

A Minecraft: Java Edition launcher for Android, forked from PojavLauncher (LGPL-3.0, see LICENSE).

## What is different from upstream
- App name "Rowan", applicationId `app.rowan.launcher` (Java namespace unchanged, so JNI keeps working).
- Two extra renderer presets in Settings > Video and renderer:
  - **Rowan Heavy**: runs on GL4ES (GLES 3 backend) with `LIBGL_USEVBO=1`.
  - **Rowan Flux**: runs on Zink (Vulkan) with `ZINK_DESCRIPTORS=lazy`.
  These are presets on engines that already ship in the app, not new native renderers.
  They are not benchmarked. Override any variable in Settings > custom env (your values win).
- Stock renderers are kept: GL4ES, Zink, and LTW (LTW is downloaded by the CI build).
- CI workflow builds `Rowan-debug.apk` (with Java runtimes) and `Rowan-debug-noruntime.apk`.

## Build the APK
1. Push this folder to a new GitHub repository.
2. Actions > "Rowan APK build" > Run workflow.
3. Download the `Rowan-debug` artifact.

The workflow downloads JRE 8/17/21 and the LTW aar from PojavLauncherTeam at build time.
If an upstream artifact has expired, that step fails; re-run later or supply the runtimes yourself.

## Status
Not built or run on a device yet. Only `RendererProfiles` has been unit-tested (plain JVM).
