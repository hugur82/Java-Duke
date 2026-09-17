import { useEffect, useState } from "react";

import RecentTransactions from "../components/dashboard/RecentTransactions";
import StatCard from "../components/dashboard/StatCard";

import { getAccounts } from "../api/accountApi";
import { getCustomers } from "../api/customerApi";
import { getTransactions, type Transaction } from "../api/transactionApi";

function Dashboard() {
  const [customerCount, setCustomerCount] = useState(0);
  const [accountCount, setAccountCount] = useState(0);
  const [transactionCount, setTransactionCount] = useState(0);

  const [recentTransactions, setRecentTransactions] = useState<Transaction[]>(
    [],
  );

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    Promise.all([getCustomers(), getAccounts(), getTransactions()])
      .then(([customers, accounts, transactions]) => {
        setCustomerCount(customers.length);
        setAccountCount(accounts.length);
        setTransactionCount(transactions.length);

        const recent = [...transactions]
          .sort(
            (a, b) =>
              new Date(b.transactionDate).getTime() -
              new Date(a.transactionDate).getTime(),
          )
          .slice(0, 5);

        setRecentTransactions(recent);
      })
      .catch(() => {
        setError("Unable to load dashboard data.");
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
            Welcome back
          </h1>

          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Here is an overview of your banking system.
          </p>
        </div>

        {loading && (
          <p className="mb-6 text-sm text-gray-500 dark:text-gray-400">
            Loading dashboard...
          </p>
        )}

        {error && (
          <p className="mb-6 text-sm font-medium text-red-600 dark:text-red-400">
            {error}
          </p>
        )}

        {!loading && !error && (
          <>
            <div className="grid grid-cols-1 gap-6 md:grid-cols-3">
              <StatCard title="Customers" value={customerCount} />

              <StatCard title="Accounts" value={accountCount} />

              <StatCard title="Transactions" value={transactionCount} />
            </div>

            <div className="mt-8">
              <RecentTransactions transactions={recentTransactions} />
            </div>
          </>
        )}
      </div>
    </main>
  );
}

export default Dashboard;
