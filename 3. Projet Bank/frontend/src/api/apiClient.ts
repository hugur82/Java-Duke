//const API_URL = "http://192.168.1.101:8080";

const API_URL = "http://localhost:8080";

const AUTH_STORAGE_KEY = "supa_bank_auth";

interface StoredCredentials {
  username: string;
  password: string;
}

export function getStoredCredentials(): StoredCredentials | null {
  const stored = sessionStorage.getItem(AUTH_STORAGE_KEY);

  if (!stored) {
    return null;
  }

  return JSON.parse(stored);
}

export function setStoredCredentials(username: string, password: string): void {
  sessionStorage.setItem(
    AUTH_STORAGE_KEY,
    JSON.stringify({
      username,
      password,
    }),
  );
}

export function clearStoredCredentials(): void {
  sessionStorage.removeItem(AUTH_STORAGE_KEY);
}

export async function apiClient(
  endpoint: string,
  options: RequestInit = {},
): Promise<Response> {
  const headers = new Headers(options.headers);

  const credentials = getStoredCredentials();

  if (credentials) {
    headers.set(
      "Authorization",
      "Basic " + btoa(`${credentials.username}:${credentials.password}`),
    );
  }

  headers.set("Content-Type", "application/json");

  return fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers,
  });
}
