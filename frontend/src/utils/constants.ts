// Centralized constants for the SSF frontend.

export const API_BASE_URL =
  (typeof import.meta !== "undefined" && (import.meta as any).env?.VITE_API_BASE_URL) ||
  "http://localhost:8080";

export const STORAGE_KEYS = {
  token: "token",
  userId: "userId",
  username: "username",
  role: "role",
  legacyUser: "ssf_user",
} as const;

export const ROLES = {
  USER: "USER",
  PROVIDER: "PROVIDER",
} as const;

export const BOOKING_STATUS = {
  PENDING: "PENDING",
  CONFIRMED: "CONFIRMED",
  COMPLETED: "COMPLETED",
  REJECTED: "REJECTED",
  CANCELLED: "CANCELLED",
} as const;

export const NOTIFICATION_TYPES = [
  "BOOKING_CREATED",
  "BOOKING_ACCEPTED",
  "BOOKING_REJECTED",
  "BOOKING_COMPLETED",
  "REVIEW_RECEIVED",
  "SYSTEM_ALERT",
] as const;

export const API_PATHS = {
  auth: {
    register: "/auth/register",
    login: "/auth/login",
  },
  user: {
    create: "/user/data",
    list: "/user/data",
    me: (id: number | string) => `/user/me/${id}`,
    update: "/user/data",
  },
  provider: {
    create: "/provider/data",
    me: "/provider/data",
    update: "/provider/data",
    delete: "/provider/data",
    getId: "/provider/getId",
  },
  service: {
    create: "/services/data",
    list: "/services/data",
    mine: "/services/data/my-services",
    byId: (id: number | string) => `/services/data/${id}`,
    delete: (id: number | string) => `/services/data/${id}`,
    byProvider: (id: number | string) => `/services/provider/${id}`,
  },
  booking: {
    create: "/booking/data",
    all: "/booking/allBooking",
    user: "/booking/userBooking",
    provider: "/booking/providerBooking",
    accept: (id: number | string) => `/booking/${id}/accept`,
    reject: (id: number | string) => `/booking/${id}/reject`,
    cancel: (id: number | string) => `/booking/${id}/cancel`,
    completed: (id: number | string) => `/booking/${id}/completed`,
  },
  review: {
    create: "/review/data",
    byProvider: (id: number | string) => `/review/provider/${id}`,
    mine: "/review/my",
    byService: (id: number | string) => `/review/service/${id}`,
    update: (id: number | string) => `/review/update/${id}`,
    delete: (id: number | string) => `/review/delete/${id}`,
    avg: (id: number | string) => `/review/provider/${id}/average-rating`,
    count: (id: number | string) => `/review/provider/${id}/count`,
  },
  notification: {
    list: (userId: number | string) => `/notifications/user/${userId}`,
    unread: (userId: number | string) => `/notifications/user/${userId}/unread-count`,
    read: (id: number | string) => `/notifications/${id}/read`,
    delete: (id: number | string) => `/notifications/${id}`,
    retry: "/notifications/retry-failed",
  },
} as const;
