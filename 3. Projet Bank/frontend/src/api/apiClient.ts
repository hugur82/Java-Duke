const API_URL = "http://localhost:8080";

const username = "root";
const password = "123qwertz";

export async function apiClient(
  endpoint: string,
  options: RequestInit = {},
): Promise<Response> {
  const headers = new Headers(options.headers);

  headers.set("Authorization", "Basic " + btoa(`${username}:${password}`));

  headers.set("Content-Type", "application/json");

  return fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers,
  });
}
