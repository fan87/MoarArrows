package com.dragoncommissions.moararrows.addons;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class NameSpace {

    private final String addonName;
    private final String namespace;

    @Override
    public String toString() {
        return addonName + getMidString() + namespace;
    }

    public NameSpace(String namespace) {
        this.addonName = getDefaultAddonName();
        this.namespace = namespace;
    }

    protected static String getMidString() {
        return "::";
    }

    protected static String getDefaultAddonName() {
        return "builtin";
    }

    public static NameSpace fromString(String input) {
        if (!input.contains(getMidString())) return new NameSpace(getDefaultAddonName(), input);
        String[] split = input.split(Pattern.quote(getMidString()));
        String addonName = split[0];
        String namespace = String.join(getMidString(), split).substring(addonName.length() + getMidString().length());
        return new NameSpace(addonName, namespace);
    }

}
