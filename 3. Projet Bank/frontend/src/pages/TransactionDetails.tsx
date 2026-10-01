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

            <div className="flex items-center gap-3">
              {transaction.transactionStatus === "CREATED" && (
                <>
                  <button
                    type="button"
                    onClick={openEditForm}
                    className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
                  >
                    Update Transaction
                  </button>
                  <button
                    type="button"
                    onClick={handleProcess}
                    disabled={processing}
                    className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
                  >
                    {processing ? "Processing..." : "Process Transaction"}
                  </button>
                </>
              )}

              <span
                className={
                  transaction.transactionStatus === "ACCEPTED"
                    ? "rounded-full bg-green-100 px-3 py-1 text-xs font-semibold text-green-700 dark:bg-green-900/30 dark:text-green-400"
                    : transaction.transactionStatus === "REJECTED"
                      ? "rounded-full bg-red-100 px-3 py-1 text-xs font-semibold text-red-700 dark:bg-red-900/30 dark:text-red-400"
                      : "rounded-full bg-amber-100 px-3 py-1 text-xs font-semibold text-amber-700 dark:bg-amber-900/30 dark:text-amber-400"
                }
              >
                {transaction.transactionStatus}
              </span>
            </div>
          </div>
        </div>

        {error && (
          <div className="mb-6 rounded-lg bg-red-100 px-4 py-3 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400">
            {error}
          </div>
        )}

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

        {showEditForm && (
          <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
            <div className="w-full max-w-md rounded-xl bg-white p-6 shadow-xl dark:bg-gray-900">
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
