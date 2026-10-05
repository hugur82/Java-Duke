import { apiClient } from "./apiClient";

export type EmployeeRole = "ADMIN" | "EMPLOYEE";

export type EmployeeStatus = "ACTIVE" | "DISABLED";

export interface Employee {
  employeeId: number;
  firstName: string;
  lastName: string;
  email: string;
  role: EmployeeRole;
  status: EmployeeStatus;
  createdAt: string;
  lastLoginAt: string | null;
}

export interface EmployeeCreateRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: EmployeeRole;
}

export interface EmployeeUpdateRequest {
  firstName: string;
  lastName: string;
  email: string;
  role: EmployeeRole;
}

export async function getEmployees(): Promise<Employee[]> {
  const response = await apiClient("/api/employees");

  if (!response.ok) {
    throw new Error("Failed to fetch employees");
  }

  return response.json();
}

export async function getEmployeeById(employeeId: number): Promise<Employee> {
  const response = await apiClient(`/api/employees/${employeeId}`);

  if (!response.ok) {
    throw new Error("Failed to fetch employee");
  }

  return response.json();
}

export async function updateEmployee(
  employeeId: number,
  employee: EmployeeUpdateRequest,
): Promise<Employee> {
  const response = await apiClient(`/api/employees/${employeeId}`, {
    method: "PUT",
    body: JSON.stringify(employee),
  });

  if (!response.ok) {
    throw new Error("Failed to update employee");
  }

  return response.json();
}

export async function updateEmployeeStatus(
  employeeId: number,
  status: EmployeeStatus,
): Promise<Employee> {
  const response = await apiClient(`/api/employees/${employeeId}/status`, {
    method: "PATCH",
    body: JSON.stringify({ status }),
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Failed to update employee status");
  }

  return response.json();
}

export async function createEmployee(
  employee: EmployeeCreateRequest,
): Promise<Employee> {
  const response = await apiClient("/api/employees", {
    method: "POST",
    body: JSON.stringify(employee),
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Failed to create employee");
  }

  return response.json();
}

export async function resetEmployeePassword(
  employeeId: number,
): Promise<string> {
  const response = await apiClient(
    `/api/employees/${employeeId}/reset-password`,
    {
      method: "POST",
    },
  );

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || "Failed to reset employee password");
  }

  return response.text();
}
