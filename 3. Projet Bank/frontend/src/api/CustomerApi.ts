import { apiClient } from "./apiClient";

export interface Customer {
  customerId: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  city: string;
  status: string;
}

export async function getCustomers(): Promise<Customer[]> {
  const response = await apiClient("/api/customers");

  if (!response.ok) {
    throw new Error("Failed to fetch customers");
  }

  return response.json();
}
