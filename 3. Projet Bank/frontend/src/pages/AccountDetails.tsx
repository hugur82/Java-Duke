import { useEffect, useMemo, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
  getAccountById,
  updateAccount,
  type Account,
} from "../api/accountApi";
import { getTransactions, type Transaction } from "../api/transactionApi";

type StatementRow = {
  transaction: Transaction;
  balance: number;
};

function AccountDetails() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const accountId = id ? Number(id) : NaN;
  const invalidId = !id || Number.isNaN(accountId);

  const [account, setAccount] = useState<Account | null>(null);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(!invalidId);
  const [error, setError] = useState("");
  const [showStatusForm, setShowStatusForm] = useState(false);
  const [selectedStatus, setSelectedStatus] = useState<
    Account["accountStatus"]
  >("ACTIVE");
  const [statusError, setStatusError] = useState("");
  const [statusLoading, setStatusLoading] = useState(false);

  useEffect(() => {
    if (invalidId) {
      return;
    }

    let cancelled = false;

    Promise.all([getAccountById(accountId), getTransactions()])
      .then(([accountData, transactionData]) => {
        if (cancelled) {
          return;
        }

        setAccount(accountData);

        const accountTransactions = transactionData
          .filter(
            (transaction) =>
              transaction.accountId === accountId &&
              transaction.transactionStatus === "ACCEPTED",
          )
          .sort(
            (a, b) =>
              new Date(a.transactionDate).getTime() -
              new Date(b.transactionDate).getTime(),
          );

        setTransactions(accountTransactions);
      })
      .catch((error: Error) => {
        if (!cancelled) {
          setError(error.message);
        }
      })
      .finally(() => {
        if (!cancelled) {
          setLoading(false);
        }
      });

    return () => {
      cancelled = true;
    };
  }, [accountId, invalidId]);

  const openStatusForm = () => {
    if (!account) {
      return;
    }

    setSelectedStatus(account.accountStatus);
    setStatusError("");
    setShowStatusForm(true);
  };

  const handleStatusUpdate = async () => {
    if (!account) {
      return;
    }

    if (selectedStatus === "CLOSED" && Number(account.balance) !== 0) {
      setStatusError("An account can be closed only when its balance is zero.");
      return;
    }

    if (
      selectedStatus === "CLOSED" &&
      !window.confirm(
        "Close this account? Transactions can no longer be processed while it is closed.",
      )
    ) {
      return;
    }

    try {
      setStatusLoading(true);
      setStatusError("");
      setAccount(
        await updateAccount(account.accountId, { status: selectedStatus }),
      );
      setShowStatusForm(false);
    } catch (err) {
      setStatusError(
        err instanceof Error ? err.message : "Unable to update account status.",
      );
    } finally {
      setStatusLoading(false);
    }
  };

  /*
   * Calculate the balance chronologically.
   *
   * The calculation starts from the opening balance
   * and applies each transaction in chronological order.
   */
  const statementRows = useMemo<StatementRow[]>(() => {
    if (!account) {
      return [];
    }

    let runningBalance = Number(account.openingBalance);

    return transactions.map((transaction) => {
      if (transaction.transactionType === "DEPOSIT") {
        runningBalance += Number(transaction.amount);
      } else {
        runningBalance -= Number(transaction.amount);
      }

      return {
        transaction,
        balance: runningBalance,
      };
    });
  }, [account, transactions]);

  const totalDebits = useMemo(() => {
    return transactions
      .filter((transaction) => transaction.transactionType === "WITHDRAWAL")
      .reduce((total, transaction) => total + Number(transaction.amount), 0);
  }, [transactions]);

  const totalCredits = useMemo(() => {
    return transactions
      .filter((transaction) => transaction.transactionType === "DEPOSIT")
      .reduce((total, transaction) => total + Number(transaction.amount), 0);
  }, [transactions]);

  if (invalidId) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-7xl">
          <p className="text-red-600 dark:text-red-400">Invalid account ID.</p>
        </div>
      </main>
    );
  }

  if (loading) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-7xl">
          <p className="text-gray-600 dark:text-gray-400">Loading account...</p>
        </div>
      </main>
    );
  }

  if (error) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-7xl">
          <button
            type="button"
            onClick={() => navigate(-1)}
            className="mb-6 text-sm font-medium text-gray-600 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
          >
            ← Back
          </button>

          <div className="rounded-xl border border-red-200 bg-red-50 p-6 dark:border-red-900 dark:bg-red-950/30">
            <p className="text-red-700 dark:text-red-400">{error}</p>
          </div>
        </div>
      </main>
    );
  }

  if (!account) {
    return null;
  }

  const currentBalance = Number(account.balance);

  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <div className="mx-auto max-w-7xl">
        {/* Back */}
        <button
          type="button"
          onClick={() => navigate(-1)}
          className="mb-6 text-sm font-medium text-gray-600 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
        >
          ← Back
        </button>

        {/* Account header */}
        <section className="mb-8 overflow-hidden rounded-2xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
          <div className="border-b border-gray-200 p-6 dark:border-gray-800">
            <div className="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between">
              <div>
                <p className="mb-1 text-sm font-medium text-gray-500 dark:text-gray-400">
                  Account statement
                </p>

                <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
                  {account.accountType === "CHECKING"
                    ? "Checking Account"
                    : "Savings Account"}
                </h1>

                <p className="mt-2 text-sm text-gray-500 dark:text-gray-400">
                  Account number: {account.accountNumber}
                </p>

                <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
                  IBAN: {account.iban}
                </p>
              </div>

              <div className="flex w-fit items-center gap-3">
                <span
                  className={
                    account.accountStatus === "ACTIVE"
                      ? "inline-flex rounded-full bg-green-100 px-3 py-1 text-sm font-medium text-green-700 dark:bg-green-900/30 dark:text-green-400"
                      : account.accountStatus === "BLOCKED"
                        ? "inline-flex rounded-full bg-red-100 px-3 py-1 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400"
                        : "inline-flex rounded-full bg-gray-100 px-3 py-1 text-sm font-medium text-gray-700 dark:bg-gray-800 dark:text-gray-300"
                  }
                >
                  {account.accountStatus}
                </span>

                <button
                  type="button"
                  onClick={openStatusForm}
                  className="rounded-lg border border-gray-300 px-3 py-1.5 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
                >
                  Change Status
                </button>
              </div>
            </div>
          </div>

          {/* Account information */}
          <div className="grid gap-6 p-6 md:grid-cols-2">
            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Account holder
              </p>

              <button
                type="button"
                onClick={() => navigate(`/customers/${account.customerId}`)}
                className="mt-1 font-semibold text-gray-900 hover:underline dark:text-white"
              >
                {account.customerFirstName} {account.customerLastName}
              </button>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Opening date
              </p>

              <p className="mt-1 font-semibold text-gray-900 dark:text-white">
                {new Date(account.creationDate).toLocaleDateString()}
              </p>
            </div>
          </div>

          {/* Summary */}
          <div className="grid gap-px border-t border-gray-200 bg-gray-200 dark:border-gray-800 dark:bg-gray-800 md:grid-cols-4">
            <div className="bg-white p-6 dark:bg-gray-900">
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Opening balance
              </p>

              <p className="mt-2 text-xl font-semibold text-gray-900 dark:text-white">
                CHF {Number(account.openingBalance).toFixed(2)}
              </p>
            </div>

            <div className="bg-white p-6 dark:bg-gray-900">
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Total debits
              </p>

              <p className="mt-2 text-xl font-semibold text-red-600 dark:text-red-400">
                CHF {totalDebits.toFixed(2)}
              </p>
            </div>

            <div className="bg-white p-6 dark:bg-gray-900">
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Total credits
              </p>

              <p className="mt-2 text-xl font-semibold text-green-600 dark:text-green-400">
                CHF {totalCredits.toFixed(2)}
              </p>
            </div>

            <div className="bg-white p-6 dark:bg-gray-900">
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Current balance
              </p>

              <p className="mt-2 text-xl font-bold text-gray-900 dark:text-white">
                CHF {currentBalance.toFixed(2)}
              </p>
            </div>
          </div>
        </section>

        {showStatusForm && (
          <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
            <div className="w-full max-w-md rounded-xl bg-white p-6 shadow-xl dark:bg-gray-900">
              <h2 className="text-xl font-bold text-gray-900 dark:text-white">
                Change Account Status
              </h2>

              <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
                Only ACTIVE accounts can process transactions.
              </p>

              {statusError && (
                <div className="mt-4 rounded-lg bg-red-100 px-4 py-3 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400">
                  {statusError}
                </div>
              )}

              <div className="mt-6">
                <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
                  Status
                </label>
                <select
                  value={selectedStatus}
                  onChange={(event) =>
                    setSelectedStatus(
                      event.target.value as Account["accountStatus"],
                    )
                  }
                  className="w-full rounded-lg border border-gray-300 bg-white px-3 py-2 text-sm outline-none focus:border-gray-500 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                >
                  <option value="ACTIVE">Active</option>
                  <option value="BLOCKED">Blocked</option>
                  <option value="CLOSED">Closed</option>
                </select>
              </div>

              <div className="mt-6 flex justify-end gap-3">
                <button
                  type="button"
                  onClick={() => setShowStatusForm(false)}
                  className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
                >
                  Cancel
                </button>
                <button
                  type="button"
                  onClick={handleStatusUpdate}
                  disabled={statusLoading || selectedStatus === account.accountStatus}
                  className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
                >
                  {statusLoading ? "Saving..." : "Save Status"}
                </button>
              </div>
            </div>
          </div>
        )}

        {/* Statement */}
        <section className="overflow-hidden rounded-2xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
          <div className="border-b border-gray-200 p-6 dark:border-gray-800">
            <h2 className="text-lg font-semibold text-gray-900 dark:text-white">
              Account movements
            </h2>

            <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
              {transactions.length} movement
              {transactions.length !== 1 ? "s" : ""}
            </p>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full text-left">
              <thead className="bg-gray-50 text-xs uppercase text-gray-500 dark:bg-gray-800/50 dark:text-gray-400">
                <tr>
                  <th className="px-6 py-4 font-medium">Date</th>

                  <th className="px-6 py-4 font-medium">Description</th>

                  <th className="px-6 py-4 text-right font-medium">Debit</th>

                  <th className="px-6 py-4 text-right font-medium">Credit</th>

                  <th className="px-6 py-4 text-right font-medium">Balance</th>
                </tr>
              </thead>

              <tbody className="divide-y divide-gray-200 dark:divide-gray-800">
                {/* Transactions : plus récente → plus ancienne */}
                {[...statementRows]
                  .reverse()
                  .map(({ transaction, balance }) => {
                    const isDeposit = transaction.transactionType === "DEPOSIT";

                    return (
                      <tr
                        key={transaction.transactionId}
                        onClick={() =>
                          navigate(`/transactions/${transaction.transactionId}`)
                        }
                        className="cursor-pointer transition-colors hover:bg-gray-50 dark:hover:bg-gray-800"
                      >
                        <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                          {new Date(
                            transaction.transactionDate,
                          ).toLocaleDateString()}
                        </td>

                        <td className="px-6 py-4">
                          <p className="text-sm font-medium text-gray-900 dark:text-white">
                            {transaction.description ||
                              (isDeposit ? "Deposit" : "Withdrawal")}
                          </p>

                          <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                            Transaction #{transaction.transactionId}
                          </p>
                        </td>

                        <td className="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                          {!isDeposit && (
                            <span className="text-red-600 dark:text-red-400">
                              CHF {Number(transaction.amount).toFixed(2)}
                            </span>
                          )}
                        </td>

                        <td className="whitespace-nowrap px-6 py-4 text-right text-sm font-medium">
                          {isDeposit && (
                            <span className="text-green-600 dark:text-green-400">
                              CHF {Number(transaction.amount).toFixed(2)}
                            </span>
                          )}
                        </td>

                        <td className="whitespace-nowrap px-6 py-4 text-right text-sm font-semibold text-gray-900 dark:text-white">
                          CHF {balance.toFixed(2)}
                        </td>
                      </tr>
                    );
                  })}

                {/* Account opening : toujours la ligne la plus ancienne */}
                <tr className="bg-gray-50 dark:bg-gray-800/30">
                  <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-700 dark:text-gray-300">
                    {new Date(account.creationDate).toLocaleDateString()}
                  </td>

                  <td className="px-6 py-4">
                    <p className="text-sm font-semibold text-gray-900 dark:text-white">
                      Account opening
                    </p>

                    <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                      Initial account balance
                    </p>
                  </td>

                  <td className="px-6 py-4" />

                  <td className="px-6 py-4 text-right text-sm font-medium text-green-600 dark:text-green-400">
                    CHF {Number(account.openingBalance).toFixed(2)}
                  </td>

                  <td className="px-6 py-4 text-right text-sm font-semibold text-gray-900 dark:text-white">
                    CHF {Number(account.openingBalance).toFixed(2)}
                  </td>
                </tr>
              </tbody>

              <tfoot className="border-t-2 border-gray-300 dark:border-gray-700">
                <tr>
                  <td
                    colSpan={4}
                    className="px-6 py-5 text-right text-sm font-semibold text-gray-700 dark:text-gray-300"
                  >
                    Final balance
                  </td>

                  <td className="px-6 py-5 text-right text-lg font-bold text-gray-900 dark:text-white">
                    CHF {currentBalance.toFixed(2)}
                  </td>
                </tr>
              </tfoot>
            </table>
          </div>
        </section>
      </div>
    </main>
  );
}

export default AccountDetails;
