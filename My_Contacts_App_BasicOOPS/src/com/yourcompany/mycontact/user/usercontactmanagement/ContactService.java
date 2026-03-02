package com.yourcompany.mycontact.user.usercontactmanagement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ContactService {

    private final Map<String, List<Contact>> contactsByOwner = new HashMap<>();

    public Contact createPersonContact(String ownerEmail, String name, String phone, String email) {
        if (ownerEmail == null || ownerEmail.isBlank())
            throw new IllegalArgumentException("Owner email required");

        String key = ownerEmail.trim().toLowerCase();
        Contact c = new PersonContact(name, new PhoneNumber(phone), email);
        contactsByOwner.computeIfAbsent(key, k -> new ArrayList<>()).add(c);
        return c;
    }

    public List<Contact> list(String ownerEmail) {
        return Collections.unmodifiableList(
                contactsByOwner.getOrDefault(ownerEmail.trim().toLowerCase(), Collections.emptyList())
        );
    }

    public Optional<Contact> findById(String ownerEmail, UUID id) {
        return list(ownerEmail).stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public int bulkSoftDelete(String ownerEmail, List<UUID> ids) {
        int count = 0;
        for (UUID id : ids) {
            Optional<Contact> c = findById(ownerEmail, id);
            if (c.isPresent() && !c.get().isDeleted()) {
                c.get().markDeleted();
                count++;
            }
        }
        return count;
    }

    public int bulkHardDelete(String ownerEmail, List<UUID> ids) {
        String key = ownerEmail.trim().toLowerCase();
        List<Contact> list = contactsByOwner.getOrDefault(key, Collections.emptyList());
        int before = list.size();
        list.removeIf(c -> ids.contains(c.getId()));
        return before - list.size();
    }

    public int bulkAddTag(String ownerEmail, List<UUID> ids, String tag) {
        int count = 0;
        for (UUID id : ids) {
            Optional<Contact> c = findById(ownerEmail, id);
            if (c.isPresent() && !c.get().isDeleted()) {
                c.get().addTag(tag);
                count++;
            }
        }
        return count;
    }

    public int bulkRemoveTag(String ownerEmail, List<UUID> ids, String tag) {
        int count = 0;
        for (UUID id : ids) {
            Optional<Contact> c = findById(ownerEmail, id);
            if (c.isPresent() && !c.get().isDeleted()) {
                c.get().removeTag(tag);
                count++;
            }
        }
        return count;
    }

    public void exportCsv(String ownerEmail, Path output, boolean onlyActive) throws IOException {
        List<Contact> list = list(ownerEmail);
        try (BufferedWriter w = Files.newBufferedWriter(output)) {
            w.write("id,type,name,phone,email,tags,createdAt,deleted,deletedAt");
            w.newLine();
            for (Contact c : list) {
                if (onlyActive && c.isDeleted()) continue;
                String tags = String.join(";", c.getTags());
                String line = String.format(
                        "%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        c.getId(),
                        c.getContactType(),
                        escape(c.getName()),
                        c.getPhone().getNumber(),
                        escape(c.getEmail() == null ? "" : c.getEmail()),
                        escape(tags),
                        c.getCreatedAt(),
                        c.isDeleted(),
                        c.getDeletedAt() == null ? "" : c.getDeletedAt().toString()
                );
                w.write(line);
                w.newLine();
            }
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        String v = s.replace("\"", "\"\"");
        if (v.contains(",") || v.contains("\"") || v.contains("\n")) {
            return "\"" + v + "\"";
        }
        return v;
        }
}