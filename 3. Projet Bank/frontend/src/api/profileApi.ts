import { apiClient } from "./apiClient";
import type { Employee, EmployeeUpdateRequest } from "./employeeApi";

export async function updateProfile(
  profile: EmployeeUpdateRequest,
): Promise<Employee> {
  const response = await apiClient("/api/profile", {
    method: "PUT",
    body: JSON.stringify(profile),
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Failed to update profile");
  }

  return response.json();
}

export async function changePassword(
  currentPassword: string,
  newPassword: string,
): Promise<void> {
  const response = await apiClient("/api/profile/password", {
    method: "PUT",
    body: JSON.stringify({
      currentPassword,
      newPassword,
    }),
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Failed to change password");
  }
}
