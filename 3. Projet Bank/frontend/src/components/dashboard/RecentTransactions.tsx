import { useNavigate } from "react-router-dom";
import type { Transaction } from "../../api/transactionApi";

interface RecentTransactionsProps {
  transactions: Transaction[];
}

function RecentTransactions({ transactions }: RecentTransactionsProps) {
  const navigate = useNavigate();

  const openTransaction = (transactionId: number) => {
    navigate(`/transactions/${transactionId}`);
  };

  return (
    <div className="rounded-xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-gray-200 px-4 py-4 sm:px-6 dark:border-gray-800">
        <div>
          <h2 className="text-lg font-semibold text-gray-900 dark:text-white">
            Recent Transactions
          </h2>

          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Latest transactions processed by the bank.
          </p>
        </div>

        <button
          type="button"
          onClick={() => navigate("/transactions")}
          className="shrink-0 text-sm font-medium text-blue-600 hover:text-blue-700 dark:text-blue-400 dark:hover:text-blue-300"
        >
          View all
        </button>
      </div>

      {/* Mobile cards */}
      <div className="space-y-3 p-4 md:hidden">
        {transactions.length > 0 ? (
          transactions.map((transaction) => (
            <button
              key={transaction.transactionId}
              type="button"
              onClick={() => openTransaction(transaction.transactionId)}
              className="w-full rounded-lg border border-gray-200 p-4 text-left transition-colors hover:bg-gray-50 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-500 dark:border-gray-800 dark:hover:bg-gray-800"
            >
              <div className="flex items-start justify-between gap-3">
                <div className="min-w-0">
                  <p className="font-semibold text-gray-900 dark:text-white">
                    {transaction.customerFirstName}{" "}
                    {transaction.customerLastName}
                  </p>

                  <p className="mt-1 break-all text-xs text-gray-500 dark:text-gray-400">
                    Account: {transaction.accountNumber}
                  </p>
                </div>

                <span className="shrink-0 text-sm font-semibold text-gray-900 dark:text-white">
                  {transaction.amount.toFixed(2)} €
                </span>
              </div>

              <div className="mt-3 flex flex-wrap items-center justify-between gap-2">
                <span
                  className={
                    transaction.transactionType === "DEPOSIT"
                      ? "text-sm font-medium text-green-600 dark:text-green-400"
                      : "text-sm font-medium text-red-600 dark:text-red-400"
                  }
                >
                  {transaction.transactionType}
                </span>

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
              </div>
            </button>
          ))
        ) : (
          <p className="py-8 text-center text-sm font-semibold text-gray-500 dark:text-gray-400">
            No transactions found.
          </p>
        )}
      </div>

      {/* Desktop table */}
      <div className="hidden overflow-x-auto md:block">
        <table className="w-full text-left">
          <thead className="border-b border-gray-200 bg-gray-50 dark:border-gray-800 dark:bg-gray-800">
            <tr>
              <th className="px-6 py-3 text-xs font-semibold uppercase tracking-wide text-gray-500 dark:text-gray-400">
                Customer
              </th>

              <th className="px-6 py-3 text-xs font-semibold uppercase tracking-wide text-gray-500 dark:text-gray-400">
                Account
              </th>

              <th className="px-6 py-3 text-xs font-semibold uppercase tracking-wide text-gray-500 dark:text-gray-400">
                Type
              </th>

              <th className="px-6 py-3 text-xs font-semibold uppercase tracking-wide text-gray-500 dark:text-gray-400">
                Amount
              </th>

              <th className="px-6 py-3 text-xs font-semibold uppercase tracking-wide text-gray-500 dark:text-gray-400">
                Status
              </th>
            </tr>
          </thead>

          <tbody className="divide-y divide-gray-200 dark:divide-gray-800">
            {transactions.length > 0 ? (
              transactions.map((transaction) => (
                <tr
                  key={transaction.transactionId}
                  onClick={() => openTransaction(transaction.transactionId)}
                  tabIndex={0}
                  onKeyDown={(event) => {
                    if (event.key === "Enter" || event.key === " ") {
                      event.preventDefault();
                      openTransaction(transaction.transactionId);
                    }
                  }}
                  className="cursor-pointer hover:bg-gray-50 focus-visible:outline-2 focus-visible:outline-blue-500 dark:hover:bg-gray-800"
                >
                  <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                    <div>
                      <p>{transaction.customerFirstName}</p>
                      <p>{transaction.customerLastName}</p>
                    </div>
                  </td>

                  <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                    {transaction.accountNumber}
                  </td>

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

                  <td className="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                    {transaction.amount.toFixed(2)} €
                  </td>

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
                  colSpan={5}
                  className="px-6 py-10 text-center text-sm font-semibold text-gray-500 dark:text-gray-400"
                >
                  No transactions found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default RecentTransactions;
