import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { getTransactionById, type Transaction } from "../api/transactionApi";

function TransactionDetails() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const transactionId = id ? Number(id) : NaN;
  const invalidId = !id || Number.isNaN(transactionId);

  const [transaction, setTransaction] = useState<Transaction | null>(null);
  const [loading, setLoading] = useState(!invalidId);
  const [error, setError] = useState("");

  useEffect(() => {
    if (invalidId) {
      return;
    }

    let cancelled = false;

    getTransactionById(transactionId)
      .then((data) => {
        if (!cancelled) {
          setTransaction(data);
        }
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
  }, [transactionId, invalidId]);

  if (invalidId) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-4xl">
          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            Invalid transaction ID.
          </p>
        </div>
      </main>
    );
  }

  if (loading) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-4xl">
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Loading transaction...
          </p>
        </div>
      </main>
    );
  }

  if (error) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-4xl">
          <button
            type="button"
            onClick={() => navigate(-1)}
            className="mb-4 text-sm text-gray-500 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
          >
            ← Back
          </button>

          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            {error}
          </p>
        </div>
      </main>
    );
  }

  if (!transaction) {
    return null;
  }

  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <div className="mx-auto max-w-4xl">
        {/* Header */}
        <div className="mb-8">
          <button
            type="button"
            onClick={() => navigate(-1)}
            className="mb-4 text-sm text-gray-500 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
          >
            ← Back
          </button>

          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
                Transaction Details
              </h1>

              <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
                Transaction #{transaction.transactionId}
              </p>
            </div>

            <span className="rounded-full bg-gray-100 px-3 py-1 text-xs font-semibold text-gray-700 dark:bg-gray-800 dark:text-gray-300">
              {transaction.transactionStatus}
            </span>
          </div>
        </div>

        {/* Transaction information */}
        <section className="rounded-xl border border-gray-200 bg-white p-6 shadow-sm dark:border-gray-800 dark:bg-gray-900">
          <h2 className="mb-6 text-lg font-semibold text-gray-900 dark:text-white">
            Transaction Information
          </h2>

          <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
            <div className="md:col-span-2 ">
              <p className="text-gray-400">Customer</p>

              <button
                type="button"
                onClick={() => navigate(`/customers/${transaction.customerId}`)}
                className="mt-2 font-medium text-gray-900 hover:underline dark:text-white text-5xl"
              >
                {transaction.customerFirstName} {transaction.customerLastName}
              </button>
            </div>
            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Transaction ID
              </p>

              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                #{transaction.transactionId}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Account
              </p>

              <button
                type="button"
                onClick={() => navigate(`/accounts/${transaction.accountId}`)}
                className="mt-1 font-medium text-gray-900 hover:underline dark:text-white"
              >
                #{transaction.accountId}
              </button>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Date</p>

              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {new Date(transaction.transactionDate).toLocaleString()}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Type</p>

              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {transaction.transactionType}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Amount</p>

              <p className="mt-1 text-2xl font-bold text-gray-900 dark:text-white">
                {transaction.amount}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Status</p>

              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {transaction.transactionStatus}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Description
              </p>

              <p className="mt-1 font-medium text-gray-900 dark:text-white">
                {transaction.description || "No description"}
              </p>
            </div>
          </div>
        </section>
      </div>
    </main>
  );
}

export default TransactionDetails;
