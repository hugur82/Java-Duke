import { useEffect, useState } from "react";
import { getTransactions, type Transaction } from "../api/transactionApi";

function Transactions() {
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    getTransactions()
      .then((data) => {
        setTransactions(data);
      })
      .catch(() => {
        setError("Unable to load transactions.");
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <div className="mx-auto max-w-7xl">
        <div className="mb-8">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Transactions
          </h1>

          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Manage bank transactions.
          </p>
        </div>

        {loading && (
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Loading transactions...
          </p>
        )}

        {error && (
          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            {error}
          </p>
        )}

        {!loading && !error && (
          <div className="overflow-x-auto rounded-xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
            <table className="w-full text-left">
              <thead className="border-b border-gray-200 bg-gray-50 dark:border-gray-800 dark:bg-gray-800">
                <tr>
                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    ID
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Customer
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Account
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Date
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Type
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Amount
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Description
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Status
                  </th>
                </tr>
              </thead>

              <tbody className="divide-y divide-gray-200 dark:divide-gray-800">
                {transactions.length > 0 ? (
                  transactions.map((transaction) => (
                    <tr
                      key={transaction.transactionId}
                      className="hover:bg-gray-50 dark:hover:bg-gray-800"
                    >
                      {/* ID */}
                      <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                        #{transaction.transactionId}
                      </td>

                      {/* Customer */}
                      <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                        <div>
                          <p>{transaction.customerFirstName}</p>
                          <p>{transaction.customerLastName}</p>
                        </div>
                      </td>

                      {/* Account */}
                      <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {transaction.accountNumber}
                      </td>

                      {/* Date */}
                      <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {new Date(transaction.transactionDate).toLocaleString()}
                      </td>

                      {/* Type */}
                      <td className="px-6 py-4 text-sm">
                        <span
                          className={
                            transaction.transactionType === "DEPOSIT"
                              ? "font-medium text-green-600 dark:text-green-400"
                              : "font-medium text-red-600 dark:text-red-400"
                          }
                        >
                          {transaction.transactionType}
                        </span>
                      </td>

                      {/* Amount */}
                      <td className="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                        {transaction.amount.toFixed(2)} €
                      </td>

                      {/* Description */}
                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {transaction.description ?? "—"}
                      </td>

                      {/* Status */}
                      <td className="px-6 py-4 text-sm">
                        <span
                          className={
                            transaction.transactionStatus === "ACCEPTED"
                              ? "rounded-full bg-green-100 px-3 py-1 text-xs font-semibold text-green-700 dark:bg-green-900/30 dark:text-green-400"
                              : transaction.transactionStatus === "REJECTED"
                                ? "rounded-full bg-red-100 px-3 py-1 text-xs font-semibold text-red-700 dark:bg-red-900/30 dark:text-red-400"
                                : "rounded-full bg-yellow-100 px-3 py-1 text-xs font-semibold text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400"
                          }
                        >
                          {transaction.transactionStatus}
                        </span>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td
                      colSpan={8}
                      className="px-6 py-10 text-center text-sm font-semibold text-gray-500 dark:text-gray-400"
                    >
                      No transactions found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </main>
  );
}

export default Transactions;
