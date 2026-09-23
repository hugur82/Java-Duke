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

export async function getTransactions(): Promise<Transaction[]> {
  const response = await apiClient("/api/transactions");

  if (!response.ok) {
    throw new Error("Failed to fetch transactions");
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
