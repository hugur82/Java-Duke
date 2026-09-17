import { apiClient } from "./apiClient";

export interface Account {
  accountId: number;
  iban: string;
  accountNumber: string;
  balance: number;
  creationDate: string;
  accountType: "CHECKING" | "SAVINGS";
  accountStatus: "ACTIVE" | "BLOCKED" | "CLOSED";
  customerId: number;
  customerFirstName: string;
  customerLastName: string;
}

export async function getAccounts(): Promise<Account[]> {
  const response = await apiClient("/api/accounts");

  if (!response.ok) {
    throw new Error("Failed to fetch accounts");
  }

  return response.json();
}
