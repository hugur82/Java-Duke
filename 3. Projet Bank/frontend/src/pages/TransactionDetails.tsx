import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
  getTransactionById,
  processTransaction,
  updateTransaction,
  type Transaction,
} from "../api/transactionApi";

function TransactionDetails() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const transactionId = id ? Number(id) : NaN;
  const invalidId = !id || Number.isNaN(transactionId);

  const [transaction, setTransaction] = useState<Transaction | null>(null);
  const [loading, setLoading] = useState(!invalidId);
  const [error, setError] = useState("");
  const [processing, setProcessing] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [updating, setUpdating] = useState(false);

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

  const handleProcess = async () => {
    if (!transaction) {
      return;
    }

    try {
      setProcessing(true);
      setError("");
      setTransaction(await processTransaction(transaction.transactionId));
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Unable to process transaction.",
      );
    } finally {
      setProcessing(false);
    }
  };

  const openEditForm = () => {
    if (!transaction) {
      return;
    }

    setAmount(String(transaction.amount));
    setDescription(transaction.description ?? "");
    setError("");
    setShowEditForm(true);
  };

  const handleUpdate = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!transaction) {
      return;
    }

    const numericAmount = Number(amount);
    if (!Number.isFinite(numericAmount) || numericAmount <= 0) {
      setError("Amount must be greater than zero.");
      return;
    }

    try {
      setUpdating(true);
      setError("");
      setTransaction(
        await updateTransaction(transaction.transactionId, {
          amount: numericAmount,
          ...(description.trim() ? { description: description.trim() } : {}),
        }),
      );
      setShowEditForm(false);
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Unable to update transaction.",
      );
    } finally {
      setUpdating(false);
    }
  };

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
    <main className="min-w-0 flex-1 overflow-y-auto overflow-x-hidden bg-gray-50 p-4 sm:p-6 lg:p-8 dark:bg-gray-950">
      <div className="mx-auto w-full min-w-0 max-w-4xl">
        {/* Header */}
        <div className="mb-8 min-w-0">
          <button
            type="button"
            onClick={() => navigate(-1)}
            className="mb-4 text-sm text-gray-500 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
          >
            ← Back
          </button>

          <div className="flex min-w-0 items-start justify-between gap-3">
            <div className="min-w-0">
              <h1 className="text-xl font-bold text-gray-900 sm:text-2xl dark:text-white">
                Transaction Details
              </h1>

              <p className="mt-1 break-words text-sm text-gray-500 dark:text-gray-400">
                Transaction #{transaction.transactionId}
              </p>
            </div>

            <span
              className={`shrink-0 rounded-full px-3 py-1 text-xs font-semibold ${
                transaction.transactionStatus === "ACCEPTED"
                  ? "bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400"
                  : transaction.transactionStatus === "REJECTED"
                    ? "bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400"
                    : "bg-amber-100 text-amber-700 dark:bg-amber-900/30 dark:text-amber-400"
              }`}
            >
              {transaction.transactionStatus}
            </span>
          </div>

          {transaction.transactionStatus === "CREATED" && (
            <div className="mt-5 grid grid-cols-2 gap-2 sm:flex sm:justify-end sm:gap-3">
              <button
                type="button"
                onClick={openEditForm}
                className="min-w-0 rounded-lg border border-gray-300 px-2 py-3 text-sm font-medium text-gray-700 hover:bg-gray-100 sm:px-4 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
              >
                Update Transaction
              </button>

              <button
                type="button"
                onClick={handleProcess}
                disabled={processing}
                className="min-w-0 rounded-lg bg-gray-900 px-2 py-3 text-sm font-medium text-white hover:bg-gray-700 disabled:cursor-not-allowed disabled:opacity-50 sm:px-4 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
              >
                {processing ? "Processing..." : "Process Transaction"}
              </button>
            </div>
          )}
        </div>

        {error && (
          <div className="mb-6 rounded-lg bg-red-100 px-4 py-3 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400">
            {error}
          </div>
        )}

        {/* Transaction information */}
        <section className="min-w-0 rounded-xl border border-gray-200 bg-white p-4 shadow-sm sm:p-6 dark:border-gray-800 dark:bg-gray-900">
          <h2 className="mb-6 text-lg font-semibold text-gray-900 dark:text-white">
            Transaction Information
          </h2>

          <div className="grid min-w-0 grid-cols-1 gap-6 md:grid-cols-2">
            <div className="min-w-0 md:col-span-2">
              <p className="text-sm text-gray-400">Customer</p>

              <button
                type="button"
                onClick={() => navigate(`/customers/${transaction.customerId}`)}
                className="mt-2 max-w-full break-words text-left text-2xl font-medium text-gray-900 hover:underline sm:text-3xl dark:text-white"
              >
                {transaction.customerFirstName} {transaction.customerLastName}
              </button>
            </div>

            <div className="min-w-0">
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Transaction ID
              </p>
              <p className="mt-1 break-words font-medium text-gray-900 dark:text-white">
                #{transaction.transactionId}
              </p>
            </div>

            <div className="min-w-0">
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

            <div className="min-w-0">
              <p className="text-sm text-gray-500 dark:text-gray-400">Date</p>
              <p className="mt-1 break-words font-medium text-gray-900 dark:text-white">
                {new Date(transaction.transactionDate).toLocaleString()}
              </p>
            </div>

            <div className="min-w-0">
              <p className="text-sm text-gray-500 dark:text-gray-400">Type</p>
              <p className="mt-1 break-words font-medium text-gray-900 dark:text-white">
                {transaction.transactionType}
              </p>
            </div>

            <div className="min-w-0">
              <p className="text-sm text-gray-500 dark:text-gray-400">Amount</p>
              <p className="mt-1 break-words text-2xl font-bold text-gray-900 dark:text-white">
                {transaction.amount}
              </p>
            </div>

            <div className="min-w-0">
              <p className="text-sm text-gray-500 dark:text-gray-400">Status</p>
              <p className="mt-1 break-words font-medium text-gray-900 dark:text-white">
                {transaction.transactionStatus}
              </p>
            </div>

            <div className="min-w-0 md:col-span-2">
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Description
              </p>
              <p className="mt-1 break-words font-medium text-gray-900 dark:text-white">
                {transaction.description || "No description"}
              </p>
            </div>
          </div>
        </section>

        {showEditForm && (
          <div className="fixed inset-0 z-50 flex items-start justify-center overflow-y-auto bg-black/50 p-4 sm:items-center">
            <div className="my-auto w-full max-w-md rounded-xl bg-white p-5 shadow-xl sm:p-6 dark:bg-gray-900">
              <h2 className="text-xl font-bold text-gray-900 dark:text-white">
                Update Transaction
              </h2>
              <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
                You can edit a transaction only while its status is CREATED.
              </p>

              <form className="mt-6 space-y-4" onSubmit={handleUpdate}>
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
                    className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-gray-500 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                  />
                </div>

                <div>
                  <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Description <span className="font-normal">(optional)</span>
                  </label>
                  <input
                    type="text"
                    value={description}
                    onChange={(event) => setDescription(event.target.value)}
                    className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm outline-none focus:border-gray-500 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                  />
                </div>

                <div className="flex justify-end gap-3 pt-2">
                  <button
                    type="button"
                    onClick={() => setShowEditForm(false)}
                    className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
                  >
                    Cancel
                  </button>
                  <button
                    type="submit"
                    disabled={updating}
                    className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
                  >
                    {updating ? "Updating..." : "Update Transaction"}
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </main>
  );
}

export default TransactionDetails;
