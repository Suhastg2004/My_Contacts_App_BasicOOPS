package com.yourcompany.mycontact.user.usercontactmanagement;

import java.util.*;
import java.util.stream.Collectors;

public class FrequentlyContactedFilter implements ContactFilter {
    private final Map<UUID, Integer> frequencyById;
    private final int minTimes;

    public FrequentlyContactedFilter(Map<UUID, Integer> frequencyById, int minTimes) {
        this.frequencyById = frequencyById == null ? Collections.emptyMap() : frequencyById;
        this.minTimes = Math.max(0, minTimes);
    }

    @Override
    public List<Contact> apply(List<Contact> contacts) {
        List<Contact> out = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.isDeleted()) continue;
            int freq = frequencyById.getOrDefault(c.getId(), 0);
            if (freq >= minTimes) out.add(c);
        }
        // optional: sort by frequency desc to make it nicer
        out.sort((a, b) -> Integer.compare(
                frequencyById.getOrDefault(b.getId(), 0),
                frequencyById.getOrDefault(a.getId(), 0)
        ));
        return out;
    }
}