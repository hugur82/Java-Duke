import { useEffect, useState } from "react";

import { getAccounts, type Account } from "../../api/accountApi";
import { createTransaction } from "../../api/transactionApi";

type TransactionType = "DEPOSIT" | "WITHDRAWAL" | "TRANSFER";

interface TransactionFormProps {
  accounts: Account[];
  onSuccess: () => void | Promise<void>;
  onCancel: () => void;
}

function TransactionForm({
  accounts,
  onSuccess,
  onCancel,
}: TransactionFormProps) {
  const [allAccounts, setAllAccounts] = useState<Account[]>([]);
  const [accountsLoading, setAccountsLoading] = useState(true);
  const [accountsLoadError, setAccountsLoadError] = useState("");

  const [accountId, setAccountId] = useState(
    String(accounts[0]?.accountId ?? ""),
  );
  const [destinationAccountId, setDestinationAccountId] = useState("");
  const [destinationOpen, setDestinationOpen] = useState(false);

  const [transactionType, setTransactionType] =
    useState<TransactionType>("DEPOSIT");
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const isTransfer = transactionType === "TRANSFER";

  useEffect(() => {
    let cancelled = false;

    const loadAccounts = async () => {
      try {
        const data = await getAccounts();

        if (!cancelled) {
          setAllAccounts(data);
          setAccountsLoadError("");
        }
      } catch (err) {
        if (!cancelled) {
          setAccountsLoadError(
            err instanceof Error ? err.message : "Unable to load accounts.",
          );
        }
      } finally {
        if (!cancelled) {
          setAccountsLoading(false);
        }
      }
    };

    loadAccounts();

    return () => {
      cancelled = true;
    };
  }, []);

  const sourceAccount = accounts.find(
    (account) => String(account.accountId) === accountId,
  );

  const currentCustomerId =
    sourceAccount?.customerId ?? accounts[0]?.customerId;

  const sameCustomerAccounts = accounts.filter(
    (account) => String(account.accountId) !== accountId,
  );

  const otherCustomerAccounts = allAccounts.filter(
    (account) =>
      account.customerId !== currentCustomerId &&
      String(account.accountId) !== accountId,
  );

  const destinationAccount = allAccounts.find(
    (account) => String(account.accountId) === destinationAccountId,
  );

  const selectDestination = (account: Account) => {
    setDestinationAccountId(String(account.accountId));
    setDestinationOpen(false);
    setError("");
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError("");

    const numericAmount = Number(amount);

    if (!Number.isFinite(numericAmount) || numericAmount <= 0) {
      setError("Amount must be greater than zero.");
      return;
    }

    if (!accountId) {
      setError("Please select an account.");
      return;
    }

    if (isTransfer) {
      if (accountsLoading) {
        setError("Please wait until the accounts have loaded.");
        return;
      }

      if (accountsLoadError) {
        setError("Unable to load destination accounts. Please try again.");
        return;
      }

      if (!destinationAccountId) {
        setError("Please select a destination account.");
        return;
      }

      if (destinationAccountId === accountId) {
        setError("Source and destination accounts must be different.");
        return;
      }

      const destinationExists = allAccounts.some(
        (account) => String(account.accountId) === destinationAccountId,
      );

      if (!destinationExists) {
        setError("The selected destination account is unavailable.");
        return;
      }
    }

    try {
      setSubmitting(true);

      await createTransaction({
        accountId: Number(accountId),
        transactionType,
        amount: numericAmount,
        ...(isTransfer
          ? { destinationAccountId: Number(destinationAccountId) }
          : {}),
        ...(description.trim() ? { description: description.trim() } : {}),
      });

      await onSuccess();
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Unable to create transaction.",
      );
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
      <div className="w-full max-w-md rounded-xl bg-white p-6 shadow-xl dark:bg-gray-900">
        <h2 className="text-xl font-bold text-gray-900 dark:text-white">
          Create Transaction
        </h2>

        <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
          Select one of this customer's accounts.
        </p>

        {error && (
          <div className="mt-4 rounded-lg bg-red-100 px-4 py-3 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400">
            {error}
          </div>
        )}

        <form className="mt-6 space-y-4" onSubmit={handleSubmit}>
          {/* Source account */}
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Source account
            </label>

            <select
              value={accountId}
              onChange={(event) => {
                setAccountId(event.target.value);
                setDestinationAccountId("");
                setDestinationOpen(false);
                setError("");
              }}
              required
              disabled={submitting}
              className="w-full rounded-lg border border-gray-300 bg-white px-3 py-2 text-sm outline-none focus:border-gray-500 disabled:opacity-50 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            >
              {accounts.map((account) => (
                <option key={account.accountId} value={account.accountId}>
                  {account.accountNumber} - {account.accountType}
                </option>
              ))}
            </select>
          </div>

          {/* Transaction type */}
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Transaction type
            </label>

            <select
              value={transactionType}
              onChange={(event) => {
                setTransactionType(event.target.value as TransactionType);
                setError("");
                setDestinationOpen(false);
              }}
              disabled={submitting}
              className="w-full rounded-lg border border-gray-300 bg-white px-3 py-2 text-sm outline-none focus:border-gray-500 disabled:opacity-50 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            >
              <option value="DEPOSIT">Deposit</option>
              <option value="WITHDRAWAL">Withdrawal</option>
              <option value="TRANSFER">Transfer</option>
            </select>
          </div>

          {/* Destination account */}
          {isTransfer && (
            <div>
              <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
                Destination account
              </label>

              {accountsLoadError && (
                <p className="mb-2 text-sm text-red-600 dark:text-red-400">
                  {accountsLoadError}
                </p>
              )}

              <div className="relative">
                <button
                  type="button"
                  aria-haspopup="listbox"
                  aria-expanded={destinationOpen}
                  disabled={
                    submitting || accountsLoading || Boolean(accountsLoadError)
                  }
                  onClick={() => setDestinationOpen((open) => !open)}
                  className="flex w-full items-center justify-between gap-3 rounded-lg border border-gray-300 bg-white px-3 py-2 text-left text-sm disabled:opacity-50 dark:border-gray-700 dark:bg-gray-800"
                >
                  {destinationAccount ? (
                    <span>
                      <span className="block text-base font-semibold text-gray-900 dark:text-white">
                        {destinationAccount.customerFirstName}{" "}
                        {destinationAccount.customerLastName}
                      </span>

                      <span className="block text-sm font-normal text-gray-500 dark:text-gray-400">
                        {destinationAccount.accountNumber} ·{" "}
                        {destinationAccount.accountType}
                      </span>
                    </span>
                  ) : (
                    <span className="text-gray-500 dark:text-gray-400">
                      {accountsLoading
                        ? "Loading accounts..."
                        : "Select a destination account"}
                    </span>
                  )}

                  <span aria-hidden="true">▾</span>
                </button>

                {destinationOpen && (
                  <div
                    role="listbox"
                    aria-label="Destination accounts"
                    className="absolute z-20 mt-1 max-h-72 w-full overflow-y-auto rounded-lg border border-gray-200 bg-white p-2 shadow-lg dark:border-gray-700 dark:bg-gray-800"
                  >
                    {/* Same customer's accounts */}
                    {sameCustomerAccounts.length > 0 && (
                      <div>
                        <p className="px-3 py-2 text-xs font-semibold uppercase tracking-wide text-gray-500 dark:text-gray-400">
                          This Customer's Accounts
                        </p>

                        {sameCustomerAccounts.map((account) => (
                          <button
                            key={account.accountId}
                            type="button"
                            role="option"
                            aria-selected={
                              destinationAccountId === String(account.accountId)
                            }
                            onClick={() => selectDestination(account)}
                            className="w-full rounded-md px-3 py-3 text-left hover:bg-gray-100 dark:hover:bg-gray-700"
                          >
                            <span className="block text-base font-semibold text-gray-900 dark:text-white">
                              {account.customerFirstName}{" "}
                              {account.customerLastName}
                            </span>

                            <span className="block text-sm font-normal text-gray-500 dark:text-gray-400">
                              {account.accountNumber} · {account.accountType}
                            </span>
                          </button>
                        ))}
                      </div>
                    )}

                    {/* Other customers' accounts */}
                    {otherCustomerAccounts.length > 0 && (
                      <div className="mt-3 border-t border-gray-300 pt-3 dark:border-gray-600">
                        <p className="px-3 py-2 text-xs font-semibold uppercase tracking-wide text-gray-500 dark:text-gray-400">
                          Other Accounts
                        </p>

                        {otherCustomerAccounts.map((account) => (
                          <button
                            key={account.accountId}
                            type="button"
                            role="option"
                            aria-selected={
                              destinationAccountId === String(account.accountId)
                            }
                            onClick={() => selectDestination(account)}
                            className="w-full rounded-md px-3 py-3 text-left hover:bg-gray-100 dark:hover:bg-gray-700"
                          >
                            <span className="block text-base font-semibold text-gray-900 dark:text-white">
                              {account.customerFirstName}{" "}
                              {account.customerLastName}
                            </span>

                            <span className="block text-sm font-normal text-gray-500 dark:text-gray-400">
                              {account.accountNumber} · {account.accountType}
                            </span>
                          </button>
                        ))}
                      </div>
                    )}

                    {!accountsLoading &&
                      sameCustomerAccounts.length === 0 &&
                      otherCustomerAccounts.length === 0 && (
                        <p className="px-3 py-3 text-sm text-gray-500 dark:text-gray-400">
                          No other destination accounts available.
                        </p>
                      )}
                  </div>
                )}
              </div>
            </div>
          )}

          {/* Amount */}
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Amount
            </label>

            <input
              type="number"
              min="0.01"
              step="0.01"
              value={amount}
              onChange={(event) => setAmount(event.target.value)}
              required
              disabled={submitting}
              className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-gray-500 disabled:opacity-50 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          {/* Description */}
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Description <span className="font-normal">(optional)</span>
            </label>

            <input
              type="text"
              value={description}
              onChange={(event) => setDescription(event.target.value)}
              disabled={submitting}
              className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-gray-500 disabled:opacity-50 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          {/* Actions */}
          <div className="flex justify-end gap-3 pt-2">
            <button
              type="button"
              onClick={onCancel}
              disabled={submitting}
              className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 disabled:cursor-not-allowed disabled:opacity-50 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
            >
              Cancel
            </button>

            <button
              type="submit"
              disabled={
                submitting ||
                accounts.length === 0 ||
                (isTransfer && (accountsLoading || Boolean(accountsLoadError)))
              }
              className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
            >
              {submitting ? "Creating..." : "Create Transaction"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default TransactionForm;
