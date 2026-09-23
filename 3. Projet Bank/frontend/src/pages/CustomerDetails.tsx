import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { getAccounts, type Account } from "../api/accountApi";
import { getCustomerById, type Customer } from "../api/customerApi";
import { getTransactions, type Transaction } from "../api/transactionApi";

function CustomerDetails() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const customerId = id ? Number(id) : NaN;
  const invalidId = !id || Number.isNaN(customerId);

  const [customer, setCustomer] = useState<Customer | null>(null);
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const [loading, setLoading] = useState(!invalidId);
  const [error, setError] = useState("");

  useEffect(() => {
    if (invalidId) {
      return;
    }

    let cancelled = false;

    Promise.all([getCustomerById(customerId), getAccounts(), getTransactions()])
      .then(([customerData, accountsData, transactionsData]) => {
        if (cancelled) {
          return;
        }

        setCustomer(customerData);

        const customerAccounts = accountsData.filter(
          (account) => account.customerId === customerId,
        );

        setAccounts(customerAccounts);

        const customerAccountIds = new Set(
          customerAccounts.map((account) => account.accountId),
        );

        const customerTransactions = transactionsData.filter((transaction) =>
          customerAccountIds.has(transaction.accountId),
        );

        setTransactions(customerTransactions);
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
  }, [customerId, invalidId]);

  if (invalidId) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-7xl">
          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            Invalid customer ID.
          </p>
        </div>
      </main>
    );
  }

  if (loading) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-7xl">
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Loading customer...
          </p>
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
            onClick={() => navigate("/customers")}
            className="mb-4 text-sm text-gray-500 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
          >
            ← Back to Customers
          </button>

          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            {error}
          </p>
        </div>
      </main>
    );
  }

  if (!customer) {
    return null;
  }

  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <div className="mx-auto max-w-7xl">
        {/* Header */}
        <div className="mb-8 flex items-center justify-between">
          <div>
            <button
              type="button"
              onClick={() => navigate("/customers")}
              className="mb-3 text-sm text-gray-500 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
            >
              ← Back to Customers
            </button>

            <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
              Customer Details
            </h1>

            <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
              Customer #{customer.customerId}
            </p>
          </div>

          <div className="flex gap-3">
            <button
              type="button"
              className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
            >
              Edit
            </button>

            <button
              type="button"
              className="rounded-lg border border-red-300 px-4 py-2 text-sm font-medium text-red-600 hover:bg-red-50 dark:border-red-800 dark:text-red-400 dark:hover:bg-red-900/20"
            >
              Delete
            </button>
          </div>
        </div>

        {/* Customer information */}
        <section className="mb-8 rounded-xl border border-gray-200 bg-white p-6 shadow-sm dark:border-gray-800 dark:bg-gray-900">
          <h2 className="mb-6 text-lg font-semibold text-gray-900 dark:text-white">
            Personal Information
          </h2>

          <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                First Name
              </p>
              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {customer.firstName}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Last Name
              </p>
              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {customer.lastName}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Email</p>
              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {customer.email}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Phone</p>
              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {customer.phone}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">City</p>
              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {customer.city}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Status</p>
              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {customer.status}
              </p>
            </div>
          </div>
        </section>

        {/* Accounts */}
        <section className="mb-8 rounded-xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
          <div className="border-b border-gray-200 px-6 py-4 dark:border-gray-800">
            <h2 className="text-lg font-semibold text-gray-900 dark:text-white">
              Accounts
            </h2>

            <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
              {accounts.length} account
              {accounts.length !== 1 ? "s" : ""}
            </p>
          </div>

          {accounts.length > 0 ? (
            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead className="bg-gray-50 dark:bg-gray-800">
                  <tr>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Account
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Type
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      IBAN
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
                  {accounts.map((account) => (
                    <tr
                      key={account.accountId}
                      onClick={() => navigate(`/accounts/${account.accountId}`)}
                      className="cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800"
                    >
                      <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                        #{account.accountId}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {account.accountType}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {account.iban}
                      </td>

                      <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                        {account.balance}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {account.accountStatus}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p className="px-6 py-8 text-center text-sm text-gray-500 dark:text-gray-400">
              No accounts found.
            </p>
          )}
        </section>

        {/* Transactions */}
        <section className="rounded-xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
          <div className="border-b border-gray-200 px-6 py-4 dark:border-gray-800">
            <h2 className="text-lg font-semibold text-gray-900 dark:text-white">
              Transactions
            </h2>

            <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
              {transactions.length} transaction
              {transactions.length !== 1 ? "s" : ""}
            </p>
          </div>

          {transactions.length > 0 ? (
            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead className="bg-gray-50 dark:bg-gray-800">
                  <tr>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Date
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Account
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Type
                    </th>
                    <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                      Amount
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
                        navigate(`/transactions/${transaction.transactionId}`)
                      }
                      className="cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800"
                    >
                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {new Date(transaction.transactionDate).toLocaleString()}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        #{transaction.accountId}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {transaction.transactionType}
                      </td>

                      <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                        {transaction.amount}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {transaction.transactionStatus}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p className="px-6 py-8 text-center text-sm text-gray-500 dark:text-gray-400">
              No transactions found.
            </p>
          )}
        </section>
      </div>
    </main>
  );
}

export default CustomerDetails;
