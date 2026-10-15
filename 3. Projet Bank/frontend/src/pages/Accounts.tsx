import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { searchAccounts, type Account } from "../api/accountApi";

function Accounts() {
  const navigate = useNavigate();

  const [accounts, setAccounts] = useState<Account[]>([]);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [accountNumber, setAccountNumber] = useState("");

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

        const data = await searchAccounts(
          firstName,
          lastName,
          accountNumber,
          page,
          20,
        );

        setAccounts(data.content);
        setTotalPages(data.totalPages);
        setTotalElements(data.totalElements);
      } catch {
        setError("Unable to load accounts.");
      } finally {
        setLoading(false);
      }
    }, 300);

    return () => {
      clearTimeout(timeoutId);
    };
  }, [firstName, lastName, accountNumber, page]);

  const handleFirstNameChange = (value: string) => {
    setFirstName(value);
    setPage(0);
  };

  const handleLastNameChange = (value: string) => {
    setLastName(value);
    setPage(0);
  };

  const handleAccountNumberChange = (value: string) => {
    setAccountNumber(value);
    setPage(0);
  };

  return (
    <main className="min-w-0 flex-1 overflow-y-auto bg-gray-50 p-4 pt-20 dark:bg-gray-950 sm:p-6">
      <div className="mx-auto max-w-7xl">
        {/* Header */}
        <div className="mb-6 sm:mb-8">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Accounts
          </h1>
          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Search and manage bank accounts.
          </p>
        </div>

        {/* Search */}
        <section className="mb-6 rounded-xl border border-gray-200 bg-white p-4 shadow-sm dark:border-gray-800 dark:bg-gray-900 sm:p-6">
          <div className="grid gap-4 md:grid-cols-3">
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

            <div>
              <label
                htmlFor="accountNumber"
                className="mb-2 block text-sm font-medium text-gray-700 dark:text-gray-300"
              >
                Account number
              </label>
              <input
                id="accountNumber"
                type="text"
                value={accountNumber}
                onChange={(event) =>
                  handleAccountNumberChange(event.target.value)
                }
                placeholder="Search by account number"
                className="w-full rounded-lg border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 outline-none transition focus:border-gray-500 focus:ring-2 focus:ring-gray-200 dark:border-gray-700 dark:bg-gray-800 dark:text-white dark:focus:border-gray-500 dark:focus:ring-gray-700"
              />
            </div>
          </div>
        </section>

        {/* Results count */}
        {!loading && !error && (
          <p className="mb-4 text-sm text-gray-500 dark:text-gray-400">
            {totalElements} account{totalElements !== 1 ? "s" : ""} found
          </p>
        )}

        {/* Loading */}
        {loading && (
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Loading accounts...
          </p>
        )}

        {/* Error */}
        {!loading && error && (
          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            {error}
          </p>
        )}

        {/* Accounts: mobile cards */}
        {!loading && !error && (
          <>
            <div className="grid grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3 xl:hidden">
              {accounts.length > 0 ? (
                accounts.map((account) => (
                  <button
                    key={account.accountId}
                    type="button"
                    onClick={() => navigate(`/accounts/${account.accountId}`)}
                    className="block w-full rounded-xl border border-gray-200 bg-white p-4 text-left shadow-sm transition-colors hover:bg-gray-50 dark:border-gray-800 dark:bg-gray-900 dark:hover:bg-gray-800"
                  >
                    <div className="flex items-start justify-between gap-3">
                      <div className="min-w-0">
                        <p className="text-xs text-gray-500 dark:text-gray-400">
                          Account #{account.accountId}
                        </p>
                        <p className="mt-1 break-words font-semibold text-gray-900 dark:text-white">
                          {account.customerFirstName} {account.customerLastName}
                        </p>
                        <p className="mt-1 break-all text-sm text-gray-500 dark:text-gray-400">
                          {account.accountNumber}
                        </p>
                      </div>

                      <span className="shrink-0 rounded-full bg-gray-100 px-2.5 py-1 text-xs font-medium text-gray-700 dark:bg-gray-800 dark:text-gray-300">
                        {account.accountStatus}
                      </span>
                    </div>

                    <div className="mt-4 border-t border-gray-100 pt-3 dark:border-gray-800">
                      <div className="flex items-center justify-between gap-3">
                        <span className="text-sm text-gray-500 dark:text-gray-400">
                          Balance
                        </span>
                        <span className="whitespace-nowrap text-base font-semibold text-gray-900 dark:text-white">
                          {account.balance.toFixed(2)} €
                        </span>
                      </div>

                      <div className="mt-2 flex items-start justify-between gap-3">
                        <span className="text-sm text-gray-500 dark:text-gray-400">
                          Type
                        </span>
                        <span className="text-right text-sm font-medium text-gray-900 dark:text-white">
                          {account.accountType}
                        </span>
                      </div>

                      <div className="mt-2">
                        <p className="text-xs text-gray-500 dark:text-gray-400">
                          IBAN
                        </p>
                        <p className="mt-1 break-all text-sm text-gray-700 dark:text-gray-300">
                          {account.iban}
                        </p>
                      </div>

                      <p className="mt-3 text-right text-sm font-medium text-gray-700 dark:text-gray-300">
                        View details →
                      </p>
                    </div>
                  </button>
                ))
              ) : (
                <p className="rounded-xl border border-gray-200 bg-white px-4 py-8 text-center text-sm text-gray-500 dark:border-gray-800 dark:bg-gray-900 dark:text-gray-400">
                  No accounts found.
                </p>
              )}
            </div>

            {/* Accounts: desktop table */}
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
                      IBAN
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Type
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Balance
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Status
                    </th>
                  </tr>
                </thead>

                <tbody className="divide-y divide-gray-200 dark:divide-gray-800">
                  {accounts.length > 0 ? (
                    accounts.map((account) => (
                      <tr
                        key={account.accountId}
                        onClick={() =>
                          navigate(`/accounts/${account.accountId}`)
                        }
                        className="cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800"
                      >
                        <td className="px-6 py-4 text-sm text-gray-900 dark:text-white">
                          #{account.accountId}
                        </td>
                        <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                          <p>{account.customerFirstName}</p>
                          <p>{account.customerLastName}</p>
                        </td>
                        <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                          {account.accountNumber}
                        </td>
                        <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                          {account.iban}
                        </td>
                        <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                          {account.accountType}
                        </td>
                        <td className="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                          {account.balance.toFixed(2)} €
                        </td>
                        <td className="px-6 py-4 text-sm dark:text-gray-300">
                          {account.accountStatus}
                        </td>
                      </tr>
                    ))
                  ) : (
                    <tr>
                      <td
                        colSpan={7}
                        className="px-6 py-10 text-center text-sm font-semibold text-gray-500 dark:text-gray-400"
                      >
                        No accounts found.
                      </td>
                    </tr>
                  )}
                </tbody>
              </table>
            </div>
          </>
        )}

        {/* Pagination */}
        {!loading && !error && totalPages > 1 && (
          <div className="mt-6 flex items-center justify-between gap-3">
            <button
              type="button"
              disabled={page === 0}
              onClick={() => setPage((currentPage) => currentPage - 1)}
              className="rounded-lg border border-gray-300 bg-white px-3 py-2 text-sm font-medium text-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 sm:px-4"
            >
              Previous
            </button>

            <span className="text-center text-sm text-gray-500 dark:text-gray-400">
              Page {page + 1} of {totalPages}
            </span>

            <button
              type="button"
              disabled={page >= totalPages - 1}
              onClick={() => setPage((currentPage) => currentPage + 1)}
              className="rounded-lg border border-gray-300 bg-white px-3 py-2 text-sm font-medium text-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:border-gray-700 dark:bg-gray-900 dark:text-gray-300 sm:px-4"
            >
              Next
            </button>
          </div>
        )}
      </div>
    </main>
  );
}

export default Accounts;
