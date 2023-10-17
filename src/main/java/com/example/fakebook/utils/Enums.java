package com.example.fakebook.utils;

public class Enums {
    public static enum Roles {
        USER, ADMIN
    }

    public static enum AccountStatus {
        ACTIVE, DELETED, INACTIVE, BLOCKED
    }

    public static enum TypeUser {
        NORMAL, VIP, SUPER_VIP
    }

    public static enum FriendshipStatus {
        APPROVED, DELETED, PENDING, BLOCKED, NONE
    }

    public static enum BlogStatus {
        ACTIVE, DELETED, INACTIVE
    }

    public static enum CommentStatus {
        ACTIVE, DELETED, INACTIVE
    }

    public static enum MessageStatus {
        SEEN, UNSEEN, HIDDEN, NOT_SEND, SEND, DELETED, CHAT, JOIN, LEAVE
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
