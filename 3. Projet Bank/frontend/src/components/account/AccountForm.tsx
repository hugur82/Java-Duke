import { useState } from "react";

import { createAccount } from "../../api/accountApi";

interface AccountFormProps {
  customerId: number;
  customerName: string;
  onSuccess: () => void;
  onCancel: () => void;
}

function AccountForm({
  customerId,
  customerName,
  onSuccess,
  onCancel,
}: AccountFormProps) {
  const [accountType, setAccountType] = useState<"CHECKING" | "SAVINGS">(
    "CHECKING",
  );
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError("");
    setSubmitting(true);

    try {
      await createAccount({ customerId, accountType });
      onSuccess();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Unable to create account.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
      <div className="w-full max-w-md rounded-xl bg-white p-6 shadow-xl dark:bg-gray-900">
        <h2 className="text-xl font-bold text-gray-900 dark:text-white">
          Create Account
        </h2>
        <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
          The new account will be linked to {customerName}.
        </p>

        {error && (
          <div className="mt-4 rounded-lg bg-red-100 px-4 py-3 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400">
            {error}
          </div>
        )}

        <form className="mt-6" onSubmit={handleSubmit}>
          <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
            Account type
          </label>
          <select
            value={accountType}
            onChange={(event) =>
              setAccountType(event.target.value as "CHECKING" | "SAVINGS")
            }
            className="w-full rounded-lg border border-gray-300 bg-white px-3 py-2 text-sm outline-none focus:border-gray-500 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
          >
            <option value="CHECKING">Checking</option>
            <option value="SAVINGS">Savings</option>
          </select>

          <div className="mt-6 flex justify-end gap-3">
            <button
              type="button"
              onClick={onCancel}
              className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={submitting}
              className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
            >
              {submitting ? "Creating..." : "Create Account"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default AccountForm;
