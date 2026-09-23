import { apiClient } from "./apiClient";

export interface Account {
  accountId: number;
  iban: string;
  accountNumber: string;
  openingBalance: number;
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

export async function getAccountById(accountId: number): Promise<Account> {
  const response = await apiClient(`/api/accounts/${accountId}`);

  if (response.status === 404) {
    throw new Error("Account not found.");
  }

  if (!response.ok) {
    throw new Error("Unable to load account.");
  }

  return response.json();
}
