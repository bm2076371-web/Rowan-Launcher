package net.kdt.pojavlaunch.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Rowan renderer profiles.
 *
 * A profile is NOT a new native renderer. It is a named preset that runs on one of the
 * engines already shipped in the app (GL4ES or Zink) and adds its own environment
 * variables on top. The native bridge only knows the base ids, so POJAV_RENDERER is
 * always set to {@link #baseId(String)}, never to the profile id.
 *
 * Pure Java on purpose (no Android imports) so it can be unit-tested on a plain JVM.
 */
public final class RendererProfiles {
    public static final String HEAVY = "opengles3_rowan_heavy";
    public static final String FLUX = "vulkan_zink_rowan_flux";

    public static final class Profile {
        public final String id;
        public final String baseId;
        public final Map<String, String> env;

        Profile(String id, String baseId, Map<String, String> env) {
            this.id = id;
            this.baseId = baseId;
            this.env = Collections.unmodifiableMap(env);
        }
    }

    private static final Map<String, Profile> PROFILES = new LinkedHashMap<>();

    static {
        // Heavy: GL4ES on a GLES 3 backend. Variable names were checked against the
        // strings of the shipped libgl4es_114.so. Not benchmarked.
        Map<String, String> heavy = new LinkedHashMap<>();
        heavy.put("LIBGL_USEVBO", "1");
        heavy.put("LIBGL_NOBANNER", "1");
        PROFILES.put(HEAVY, new Profile(HEAVY, "opengles3", heavy));

        // Flux: Zink on Vulkan. Variable name checked against the shipped libOSMesa.so.
        // Not benchmarked.
        Map<String, String> flux = new LinkedHashMap<>();
        flux.put("ZINK_DESCRIPTORS", "lazy");
        PROFILES.put(FLUX, new Profile(FLUX, "vulkan_zink", flux));
    }

    private RendererProfiles() {}

    public static boolean isProfile(String id) {
        return id != null && PROFILES.containsKey(id);
    }

    /** The engine id the native side understands. Non-profile ids are returned unchanged. */
    public static String baseId(String id) {
        Profile p = id == null ? null : PROFILES.get(id);
        return p != null ? p.baseId : id;
    }

    /** Extra environment for the id, empty if it is not a profile. */
    public static Map<String, String> envFor(String id) {
        Profile p = id == null ? null : PROFILES.get(id);
        return p != null ? p.env : Collections.<String, String>emptyMap();
    }
}
