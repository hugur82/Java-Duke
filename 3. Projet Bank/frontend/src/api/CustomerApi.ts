import { apiClient } from "./apiClient";

export type CustomerStatus = "ACTIVE" | "INACTIVE" | "SUSPENDED";

export interface Customer {
  customerId: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  city: string;
  status: CustomerStatus;
}

export interface CustomerCreateRequest {
  firstName: string;
  lastName: string;
  birthDate: string;
  phone: string;
  email: string;
  password: string;
  address: string;
  postalCode: string;
  city: string;
  status: CustomerStatus;
}

export async function getCustomers(): Promise<Customer[]> {
  const response = await apiClient("/api/customers");

  if (!response.ok) {
    throw new Error("Failed to fetch customers");
  }

  return response.json();
}

export async function createCustomer(
  customer: CustomerCreateRequest,
): Promise<Customer> {
  const response = await apiClient("/api/customers", {
    method: "POST",
    body: JSON.stringify(customer),
  });

  if (!response.ok) {
    throw new Error("Failed to create customer");
  }

  return response.json();
}

export async function getCustomerById(customerId: number): Promise<Customer> {
  const response = await apiClient(`/api/customers/${customerId}`);

  if (response.status === 404) {
    throw new Error("Customer not found.");
  }

  if (!response.ok) {
    throw new Error("Unable to load customer.");
  }

  return response.json();
}

export async function updateCustomer(
  customerId: number,
  customer: CustomerCreateRequest,
): Promise<Customer> {
  const response = await apiClient(`/api/customers/${customerId}`, {
    method: "PUT",
    body: JSON.stringify(customer),
  });

  if (!response.ok) {
    throw new Error("Failed to update customer");
  }

  return response.json();
}

export async function deleteCustomer(customerId: number): Promise<void> {
  const response = await apiClient(`/api/customers/${customerId}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Failed to delete customer");
  }
}
