import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { searchTransactions, type Transaction } from "../api/transactionApi";

function Transactions() {
  const navigate = useNavigate();

  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [accountId, setAccountId] = useState("");

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const timeoutId = setTimeout(async () => {
      try {
        setLoading(true);
        setError("");

        const data = await searchTransactions(
          firstName,
          lastName,
          accountId,
          page,
          20,
        );

        setTransactions(data.content);
        setTotalPages(data.totalPages);
        setTotalElements(data.totalElements);
      } catch {
        setError("Unable to load transactions.");
      } finally {
        setLoading(false);
      }
    }, 300);

    return () => {
      clearTimeout(timeoutId);
    };
  }, [firstName, lastName, accountId, page]);

  const handleFirstNameChange = (value: string) => {
    setFirstName(value);
    setPage(0);
  };

  const handleLastNameChange = (value: string) => {
    setLastName(value);
    setPage(0);
  };

  const handleAccountIdChange = (value: string) => {
    setAccountId(value);
    setPage(0);
  };

  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <div className="mx-auto max-w-7xl">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Transactions
          </h1>

          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Search and manage bank transactions.
          </p>
        </div>

        {/* Search */}
        <section className="mb-6 rounded-xl border border-gray-200 bg-white p-6 shadow-sm dark:border-gray-800 dark:bg-gray-900">
          <div className="grid gap-4 md:grid-cols-3">
            {/* First name */}
            <div>
              <label
                htmlFor="firstName"
                className="mb-2 block text-sm font-medium text-gray-700 dark:text-gray-300"
              >
                First name
              </label>

              <input
                id="firstName"
                type="text"
                value={firstName}
                onChange={(event) => handleFirstNameChange(event.target.value)}
                placeholder="Search by first name"
                className="w-full rounded-lg border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 outline-none transition focus:border-gray-500 focus:ring-2 focus:ring-gray-200 dark:border-gray-700 dark:bg-gray-800 dark:text-white dark:focus:border-gray-500 dark:focus:ring-gray-700"
              />
            </div>

            {/* Last name */}
            <div>
              <label
                htmlFor="lastName"
                className="mb-2 block text-sm font-medium text-gray-700 dark:text-gray-300"
              >
                Last name
              </label>

              <input
                id="lastName"
                type="text"
                value={lastName}
                onChange={(event) => handleLastNameChange(event.target.value)}
                placeholder="Search by last name"
                className="w-full rounded-lg border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 outline-none transition focus:border-gray-500 focus:ring-2 focus:ring-gray-200 dark:border-gray-700 dark:bg-gray-800 dark:text-white dark:focus:border-gray-500 dark:focus:ring-gray-700"
              />
            </div>

            {/* Account ID */}
            <div>
              <label
                htmlFor="accountId"
                className="mb-2 block text-sm font-medium text-gray-700 dark:text-gray-300"
              >
                Account ID
              </label>

              <input
                id="accountId"
                type="number"
                min="1"
                value={accountId}
                onChange={(event) => handleAccountIdChange(event.target.value)}
                placeholder="Search by account ID"
                className="w-full rounded-lg border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 outline-none transition focus:border-gray-500 focus:ring-2 focus:ring-gray-200 dark:border-gray-700 dark:bg-gray-800 dark:text-white dark:focus:border-gray-500 dark:focus:ring-gray-700"
              />
            </div>
          </div>
        </section>

        {/* Results count */}
        {!loading && !error && (
          <p className="mb-4 text-sm text-gray-500 dark:text-gray-400">
            {totalElements} transaction
            {totalElements !== 1 ? "s" : ""} found
          </p>
        )}

        {/* Loading */}
        {loading && (
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Loading transactions...
          </p>
        )}

        {/* Error */}
        {!loading && error && (
          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            {error}
          </p>
        )}

        {/* Table */}
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
                      onClick={() =>
                        navigate(`/transactions/${transaction.transactionId}`)
                      }
                      className="cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800"
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

        {/* Pagination */}
        {!loading && !error && totalPages > 1 && (
          <div className="mt-6 flex items-center justify-between">
            <button
              type="button"
              disabled={page === 0}
              onClick={() => setPage((currentPage) => currentPage - 1)}
              className="rounded-lg border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300"
            >
              Previous
            </button>

            <span className="text-sm text-gray-500 dark:text-gray-400">
              Page {page + 1} of {totalPages}
            </span>

            <button
              type="button"
              disabled={page >= totalPages - 1}
              onClick={() => setPage((currentPage) => currentPage + 1)}
              className="rounded-lg border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300"
            >
              Next
            </button>
          </div>
        )}
      </div>
    </main>
  );
}

export default Transactions;
