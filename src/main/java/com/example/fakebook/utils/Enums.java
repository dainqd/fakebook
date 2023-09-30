package com.example.fakebook.utils;

public class Enums {
    public static enum Roles {
        USER, ADMIN
    }

    public static enum AccountStatus {
        ACTIVE, DELETED, INACTIVE, BLOCKED
    }

    public static enum FriendshipStatus {
        ACTIVE, DELETED, INACTIVE, BLOCKED
    }

    public static enum BlogStatus {
        ACTIVE, DELETED, INACTIVE
    }

    public static enum CommentStatus {
        ACTIVE, DELETED, INACTIVE
    }

    public static enum MessageStatus {
        SEEN, UNSEEN, HIDDEN, NOT_SEND, SEND, DELETED
    }

    public static enum NotificationStatus {
        SEEN, UNSEEN, DELETED
    }

    public static enum GroupStatus {
        ACTIVE, DELETED, INACTIVE
    }

    public static enum GroupMembersStatus {
        ACTIVE, DELETED, INACTIVE
    }
}
