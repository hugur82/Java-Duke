import { apiClient } from "./apiClient";

export interface Transaction {
  transactionId: number;
  transactionDate: string;
  amount: number;
  transactionType: "DEPOSIT" | "WITHDRAWAL";
  description: string | null;
  transactionStatus: "CREATED" | "PROCESSING" | "ACCEPTED" | "REJECTED";

  accountId: number;
  accountNumber: string;

  customerId: number;
  customerFirstName: string;
  customerLastName: string;
}

export interface TransactionPage {
  content: Transaction[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface TransactionCreateRequest {
  accountId: number;
  transactionType: "DEPOSIT" | "WITHDRAWAL";
  amount: number;
  description?: string;
}

export interface TransactionUpdateRequest {
  amount: number;
  description?: string;
}

export async function getTransactions(): Promise<Transaction[]> {
  const response = await apiClient("/api/transactions");

  if (!response.ok) {
    throw new Error("Failed to fetch transactions");
  }

  return response.json();
}

export async function searchTransactions(
  firstName: string,
  lastName: string,
  accountId: string,
  page = 0,
  size = 20,
): Promise<TransactionPage> {
  const params = new URLSearchParams();

  if (firstName.trim()) {
    params.set("firstName", firstName.trim());
  }

  if (lastName.trim()) {
    params.set("lastName", lastName.trim());
  }

  if (accountId.trim()) {
    params.set("accountId", accountId.trim());
  }

  params.set("page", String(page));
  params.set("size", String(size));

  const response = await apiClient(
    `/api/transactions/search?${params.toString()}`,
  );

  if (!response.ok) {
    throw new Error("Unable to search transactions.");
  }

  return response.json();
}

export async function getTransactionById(
  transactionId: number,
): Promise<Transaction> {
  const response = await apiClient(`/api/transactions/${transactionId}`);

  if (response.status === 404) {
    throw new Error("Transaction not found.");
  }

  if (!response.ok) {
    throw new Error("Unable to load transaction.");
  }

  return response.json();
}

export async function createTransaction(
  transaction: TransactionCreateRequest,
): Promise<Transaction> {
  const response = await apiClient("/api/transactions", {
    method: "POST",
    body: JSON.stringify(transaction),
  });

  if (!response.ok) {
    throw new Error("Unable to create transaction.");
  }

  return response.json();
}

export async function processTransaction(
  transactionId: number,
): Promise<Transaction> {
  const response = await apiClient(`/api/transactions/${transactionId}/process`, {
    method: "PATCH",
  });

  if (!response.ok) {
    throw new Error("Unable to process transaction.");
  }

  return response.json();
}

export async function updateTransaction(
  transactionId: number,
  transaction: TransactionUpdateRequest,
): Promise<Transaction> {
  const response = await apiClient(`/api/transactions/${transactionId}`, {
    method: "PATCH",
    body: JSON.stringify(transaction),
  });

  if (!response.ok) {
    throw new Error("Unable to update transaction.");
  }

  return response.json();
}
