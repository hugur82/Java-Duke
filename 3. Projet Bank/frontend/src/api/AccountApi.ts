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

export interface AccountPage {
  content: Account[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export async function getAccounts(): Promise<Account[]> {
  const response = await apiClient("/api/accounts");

  if (!response.ok) {
    throw new Error("Failed to fetch accounts");
  }

  return response.json();
}

export async function searchAccounts(
  firstName: string,
  lastName: string,
  accountNumber: string,
  page = 0,
  size = 20,
): Promise<AccountPage> {
  const params = new URLSearchParams();

  if (firstName.trim()) {
    params.set("firstName", firstName.trim());
  }

  if (lastName.trim()) {
    params.set("lastName", lastName.trim());
  }

  if (accountNumber.trim()) {
    params.set("accountNumber", accountNumber.trim());
  }

  params.set("page", String(page));
  params.set("size", String(size));

  const response = await apiClient(`/api/accounts/search?${params.toString()}`);

  if (!response.ok) {
    throw new Error("Unable to search accounts.");
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
