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
    <main className="min-w-0 flex-1 overflow-y-auto  overflow-x-hidden bg-gray-50 p-4 dark:bg-gray-950 sm:p-6">
      <div className="mx-auto max-w-7xl min-w-0">
        {/* Header */}
        <div className="mb-6 sm:mb-8">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Transactions
          </h1>
          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Search and manage bank transactions.
          </p>
        </div>

        {/* Search */}
        <section className="mb-6 rounded-xl border border-gray-200 bg-white p-4 shadow-sm dark:border-gray-800 dark:bg-gray-900 sm:p-6">
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
            {totalElements} transaction{totalElements !== 1 ? "s" : ""} found
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

        {/* Results */}
        {!loading && !error && (
          <>
            {transactions.length > 0 ? (
              <>
                {/* Mobile cards */}
                {/* Cards */}
                <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3 xl:hidden">
                  {transactions.map((transaction) => (
                    <button
                      key={transaction.transactionId}
                      type="button"
                      onClick={() =>
                        navigate(`/transactions/${transaction.transactionId}`)
                      }
                      className="w-full min-w-0 rounded-xl border border-gray-200 bg-white p-4 text-left shadow-sm transition hover:border-gray-300 hover:shadow-md dark:border-gray-800 dark:bg-gray-900 dark:hover:border-gray-700"
                    >
                      {/* Card header */}
                      <div className="flex items-start justify-between gap-3">
                        <div className="min-w-0">
                          <p className="text-xs font-medium text-gray-500 dark:text-gray-400">
                            Transaction #{transaction.transactionId}
                          </p>
                          <p className="mt-1 break-words font-semibold text-gray-900 dark:text-white">
                            {transaction.customerFirstName}{" "}
                            {transaction.customerLastName}
                          </p>
                        </div>

                        <span
                          className={
                            transaction.transactionStatus === "ACCEPTED"
                              ? "shrink-0 rounded-full bg-green-100 px-2.5 py-1 text-xs font-semibold text-green-700 dark:bg-green-900/30 dark:text-green-400"
                              : transaction.transactionStatus === "REJECTED"
                                ? "shrink-0 rounded-full bg-red-100 px-2.5 py-1 text-xs font-semibold text-red-700 dark:bg-red-900/30 dark:text-red-400"
                                : "shrink-0 rounded-full bg-yellow-100 px-2.5 py-1 text-xs font-semibold text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400"
                          }
                        >
                          {transaction.transactionStatus}
                        </span>
                      </div>

                      {/* Amount and type */}
                      <div className="mt-4 flex items-center justify-between gap-3">
                        <span
                          className={
                            transaction.transactionType === "DEPOSIT"
                              ? "text-sm font-semibold text-green-600 dark:text-green-400"
                              : "text-sm font-semibold text-red-600 dark:text-red-400"
                          }
                        >
                          {transaction.transactionType}
                        </span>

                        <span className="shrink-0 text-lg font-bold text-gray-900 dark:text-white">
                          {transaction.amount.toFixed(2)} €
                        </span>
                      </div>

                      {/* Details */}
                      <div className="mt-4 space-y-3 border-t border-gray-100 pt-3 dark:border-gray-800">
                        <div className="flex items-start justify-between gap-3 text-sm">
                          <span className="shrink-0 text-gray-500 dark:text-gray-400">
                            Account
                          </span>
                          <span className="min-w-0 break-all text-right text-gray-900 dark:text-white">
                            {transaction.accountNumber}
                          </span>
                        </div>

                        <div className="flex items-start justify-between gap-3 text-sm">
                          <span className="shrink-0 text-gray-500 dark:text-gray-400">
                            Date
                          </span>
                          <span className="min-w-0 text-right text-gray-700 dark:text-gray-300">
                            {new Date(
                              transaction.transactionDate,
                            ).toLocaleString()}
                          </span>
                        </div>

                        <div className="flex items-start justify-between gap-3 text-sm">
                          <span className="shrink-0 text-gray-500 dark:text-gray-400">
                            Description
                          </span>
                          <span className="min-w-0 break-words text-right text-gray-700 dark:text-gray-300">
                            {transaction.description ?? "—"}
                          </span>
                        </div>
                      </div>

                      <div className="mt-4 flex justify-end">
                        <span className="text-sm font-medium text-blue-600 dark:text-blue-400">
                          View details →
                        </span>
                      </div>
                    </button>
                  ))}
                </div>

                {/* Desktop table */}
                <div className="hidden overflow-x-hidden rounded-xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900 xl:block">
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
                      {transactions.map((transaction) => (
                        <tr
                          key={transaction.transactionId}
                          onClick={() =>
                            navigate(
                              `/transactions/${transaction.transactionId}`,
                            )
                          }
                          className="cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800"
                        >
                          <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                            #{transaction.transactionId}
                          </td>

                          <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                            <div>
                              <p>{transaction.customerFirstName}</p>
                              <p>{transaction.customerLastName}</p>
                            </div>
                          </td>

                          <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                            {transaction.accountNumber}
                          </td>

                          <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                            {new Date(
                              transaction.transactionDate,
                            ).toLocaleString()}
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

                          <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                            {transaction.description ?? "—"}
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
                      ))}
                    </tbody>
                  </table>
                </div>
              </>
            ) : (
              <div className="rounded-xl border border-gray-200 bg-white px-4 py-10 text-center text-sm font-semibold text-gray-500 shadow-sm dark:border-gray-800 dark:bg-gray-900 dark:text-gray-400">
                No transactions found.
              </div>
            )}
          </>
        )}

        {/* Pagination */}
        {!loading && !error && totalPages > 1 && (
          <div className="mt-6 flex flex-wrap items-center justify-between gap-3">
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
