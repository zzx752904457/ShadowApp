package com.tencent.shadow.sample.host.lib;

import java.util.HashMap;
import java.util.Map;

public class HostContainerHolder {
    public final static Map<Integer, HostAddPluginViewContainer> viewInstances = new HashMap<>();
    public final static Map<String, HostGetPluginFragmentContainer> fragmentInstances = new HashMap<>();
    public final static Map<String, HostGetPluginMMKVContainer> mmkvInstances = new HashMap<>();
}
