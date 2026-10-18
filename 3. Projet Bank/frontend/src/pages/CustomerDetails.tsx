import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { getAccounts, type Account } from "../api/accountApi";
import {
  deleteCustomer,
  getCustomerById,
  updateCustomerPassword,
  type Customer,
} from "../api/customerApi";
import { getTransactions, type Transaction } from "../api/transactionApi";
import AccountForm from "../components/account/AccountForm";
import CustomerForm from "../components/customer/CustomerForm";
import TransactionForm from "../components/transaction/TransactionForm";

function CustomerDetails() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [customer, setCustomer] = useState<Customer | null>(null);
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showEditForm, setShowEditForm] = useState(false);
  const [showAccountForm, setShowAccountForm] = useState(false);
  const [showTransactionForm, setShowTransactionForm] = useState(false);

  const [showPasswordForm, setShowPasswordForm] = useState(false);
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [passwordError, setPasswordError] = useState("");
  const [passwordSuccess, setPasswordSuccess] = useState("");
  const [passwordLoading, setPasswordLoading] = useState(false);

  useEffect(() => {
    if (!id) {
      setError("Customer ID is missing.");
      setLoading(false);
      return;
    }

    const customerId = Number(id);

    const loadData = async () => {
      try {
        setLoading(true);
        setError("");

        const [customerData, accountsData, transactionsData] =
          await Promise.all([
            getCustomerById(customerId),
            getAccounts(),
            getTransactions(),
          ]);

        setCustomer(customerData);

        setAccounts(
          accountsData.filter((account) => account.customerId === customerId),
        );

        // Include transactions sent and received by this customer.
        setTransactions(
          transactionsData.filter(
            (transaction) =>
              transaction.customerId === customerId ||
              transaction.destinationCustomerId === customerId,
          ),
        );
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "Unable to load customer.",
        );
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, [id]);

  const reloadCustomer = async () => {
    if (!id) return;

    const customerData = await getCustomerById(Number(id));
    setCustomer(customerData);
  };

  const reloadAccountsAndTransactions = async () => {
    if (!id) return;

    const customerId = Number(id);

    const [accountsData, transactionsData] = await Promise.all([
      getAccounts(),
      getTransactions(),
    ]);

    setAccounts(
      accountsData.filter((account) => account.customerId === customerId),
    );

    // Include transactions sent and received by this customer.
    setTransactions(
      transactionsData.filter(
        (transaction) =>
          transaction.customerId === customerId ||
          transaction.destinationCustomerId === customerId,
      ),
    );
  };

  const handleDelete = async () => {
    if (!customer) return;

    const confirmed = window.confirm(
      `Are you sure you want to delete ${customer.firstName} ${customer.lastName}?`,
    );

    if (!confirmed) return;

    try {
      await deleteCustomer(customer.customerId);
      navigate("/customers");
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Unable to delete customer.",
      );
    }
  };

  const handleChangePassword = async () => {
    if (!customer) return;

    setPasswordError("");
    setPasswordSuccess("");

    if (!currentPassword || !newPassword || !confirmPassword) {
      setPasswordError("Please fill in all password fields.");
      return;
    }

    if (newPassword !== confirmPassword) {
      setPasswordError("New passwords do not match.");
      return;
    }

    try {
      setPasswordLoading(true);

      await updateCustomerPassword(customer.customerId, {
        currentPassword,
        newPassword,
      });

      setPasswordSuccess("Password updated successfully.");

      setCurrentPassword("");
      setNewPassword("");
      setConfirmPassword("");
    } catch (err) {
      setPasswordError(
        err instanceof Error ? err.message : "Unable to update password.",
      );
    } finally {
      setPasswordLoading(false);
    }
  };

  if (loading) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-7xl">
          <p className="text-gray-600 dark:text-gray-400">
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
          <div className="rounded-lg bg-red-100 px-4 py-3 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400">
            {error}
          </div>
        </div>
      </main>
    );
  }

  if (!customer) {
    return (
      <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
        <div className="mx-auto max-w-7xl">
          <p className="text-gray-600 dark:text-gray-400">
            Customer not found.
          </p>
        </div>
      </main>
    );
  }

  // Sort transactions from newest to oldest.
  const sortedTransactions = [...transactions].sort(
    (a, b) =>
      new Date(b.transactionDate).getTime() -
      new Date(a.transactionDate).getTime(),
  );

  const formatTransactionDate = (date: string) =>
    date.replace("T", " ").slice(0, 16);

  const getTransactionStatusClasses = (
    status: Transaction["transactionStatus"],
  ) =>
    ({
      CREATED:
        "bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400",
      PROCESSING:
        "bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400",
      ACCEPTED:
        "bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400",
      REJECTED: "bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400",
    })[status];

  // Determine the direction of a transaction from this customer's perspective.
  const getTransactionDirection = (transaction: Transaction) => {
    const isIncomingTransfer =
      transaction.transactionType === "TRANSFER" &&
      transaction.destinationCustomerId === customer.customerId;

    const isOutgoingTransfer =
      transaction.transactionType === "TRANSFER" &&
      transaction.customerId === customer.customerId;

    const isCredit =
      transaction.transactionType === "DEPOSIT" || isIncomingTransfer;

    const isDebit =
      transaction.transactionType === "WITHDRAWAL" || isOutgoingTransfer;

    return { isCredit, isDebit };
  };

  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <div className="mx-auto max-w-7xl">
        {/* Header */}
        <div className="mb-6 flex flex-col gap-4">
          <button
            type="button"
            onClick={() => navigate("/customers")}
            className="self-start rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
          >
            ← Back to Customers
          </button>

          <div className="grid grid-cols-2 gap-2 lg:grid-cols-5 lg:gap-3">
            <button
              type="button"
              onClick={() => setShowEditForm(true)}
              className="rounded-lg bg-gray-900 px-3 py-3 text-sm font-medium text-white hover:bg-gray-700 sm:px-4 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
            >
              Edit
            </button>

            <button
              type="button"
              onClick={() => {
                setShowPasswordForm(true);
                setPasswordError("");
                setPasswordSuccess("");
              }}
              className="rounded-lg border border-gray-300 px-3 py-3 text-sm font-medium text-gray-700 hover:bg-gray-100 sm:px-4 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
            >
              Change Password
            </button>

            <button
              type="button"
              onClick={() => setShowAccountForm(true)}
              className="rounded-lg border border-gray-300 px-3 py-3 text-sm font-medium text-gray-700 hover:bg-gray-100 sm:px-4 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
            >
              Create Account
            </button>

            <button
              type="button"
              onClick={() => setShowTransactionForm(true)}
              disabled={accounts.length === 0}
              title={
                accounts.length === 0
                  ? "This customer has no accounts. Create an account first."
                  : undefined
              }
              className="rounded-lg border border-gray-300 px-3 py-3 text-sm font-medium text-gray-700 hover:bg-gray-100 disabled:cursor-not-allowed disabled:opacity-50 sm:px-4 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
            >
              Create Transaction
            </button>

            <button
              type="button"
              onClick={handleDelete}
              className="col-span-2 rounded-lg bg-red-600 px-3 py-3 text-sm font-medium text-white hover:bg-red-700 sm:px-4 lg:col-span-1"
            >
              Delete Customer
            </button>
          </div>
        </div>

        {/* Customer information */}
        <div className="mb-8 rounded-xl bg-white p-6 shadow dark:bg-gray-900">
          <h1 className="mb-6 text-3xl font-bold text-gray-900 dark:text-white">
            {customer.firstName} {customer.lastName}
          </h1>

          <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                First name
              </p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.firstName}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Last name
              </p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.lastName}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Email</p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.email}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Phone</p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.phone}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Birth date
              </p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.birthDate}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Address
              </p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.address}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Postal code
              </p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.postalCode}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">City</p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.city}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Status</p>
              <p className="font-medium text-gray-900 dark:text-white">
                {customer.status}
              </p>
            </div>
          </div>
        </div>

        {/* Accounts */}
        <div className="mb-8">
          <h2 className="mb-4 text-xl font-bold text-gray-900 dark:text-white">
            Accounts
          </h2>

          {/* Mobile account cards */}
          <div className="space-y-2 md:hidden">
            {accounts.map((account) => (
              <button
                key={account.accountId}
                type="button"
                onClick={() => navigate(`/accounts/${account.accountId}`)}
                className="w-full rounded-lg border border-gray-200 bg-white px-3 py-2 text-left shadow-sm transition hover:border-gray-300 dark:border-gray-800 dark:bg-gray-900 dark:hover:border-gray-700"
              >
                <div className="flex items-center justify-between gap-2">
                  <span className="min-w-0 truncate text-sm font-semibold text-gray-900 dark:text-white">
                    {account.accountNumber}
                  </span>

                  <span
                    className={`shrink-0 rounded-full px-2 py-0.5 text-xs font-semibold ${
                      account.accountStatus === "ACTIVE"
                        ? "bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400"
                        : "bg-gray-100 text-gray-600 dark:bg-gray-800 dark:text-gray-300"
                    }`}
                  >
                    {account.accountStatus}
                  </span>
                </div>

                <div className="mt-1 flex items-center justify-between gap-2">
                  <span className="truncate text-xs text-gray-500 dark:text-gray-400">
                    {account.accountType}
                  </span>
                  <span className="shrink-0 text-sm font-medium text-gray-900 dark:text-white">
                    {Number(account.balance).toFixed(2)}
                  </span>
                </div>
              </button>
            ))}

            {accounts.length === 0 && (
              <div className="rounded-lg bg-white px-4 py-5 text-center text-sm text-gray-500 shadow dark:bg-gray-900 dark:text-gray-400">
                No accounts found.
              </div>
            )}
          </div>

          {/* Desktop account table */}
          <div className="hidden overflow-x-auto rounded-xl bg-white shadow dark:bg-gray-900 md:block">
            <table className="w-full text-left text-sm">
              <thead className="border-b border-gray-200 bg-gray-50 dark:border-gray-700 dark:bg-gray-800">
                <tr>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Account number
                  </th>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Type
                  </th>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Balance
                  </th>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Status
                  </th>
                </tr>
              </thead>

              <tbody>
                {accounts.map((account) => (
                  <tr
                    key={account.accountId}
                    onClick={() => navigate(`/accounts/${account.accountId}`)}
                    className="cursor-pointer border-b border-gray-100 hover:bg-gray-50 dark:border-gray-800 dark:hover:bg-gray-800"
                  >
                    <td className="px-4 py-3 font-medium text-gray-900 dark:text-white">
                      {account.accountNumber}
                    </td>
                    <td className="px-4 py-3 text-gray-900 dark:text-white">
                      {account.accountType}
                    </td>
                    <td className="px-4 py-3 text-gray-900 dark:text-white">
                      {account.balance}
                    </td>
                    <td className="px-4 py-3 text-gray-900 dark:text-white">
                      {account.accountStatus}
                    </td>
                  </tr>
                ))}

                {accounts.length === 0 && (
                  <tr>
                    <td
                      colSpan={4}
                      className="px-4 py-6 text-center text-gray-500 dark:text-gray-400"
                    >
                      No accounts found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {/* Transactions */}
        <div>
          <h2 className="mb-4 text-xl font-bold text-gray-900 dark:text-white">
            Transactions
          </h2>

          {/* Mobile transaction cards */}
          <div className="space-y-3 lg:hidden">
            {sortedTransactions.map((transaction) => {
              const { isCredit, isDebit } =
                getTransactionDirection(transaction);
              const statusClasses = getTransactionStatusClasses(
                transaction.transactionStatus,
              );

              return (
                <button
                  key={transaction.transactionId}
                  type="button"
                  onClick={() =>
                    navigate(`/transactions/${transaction.transactionId}`)
                  }
                  className="w-full min-w-0 rounded-xl border border-gray-200 bg-white p-3 text-left shadow-sm transition hover:border-gray-300 dark:border-gray-800 dark:bg-gray-900 dark:hover:border-gray-700"
                >
                  <div className="flex min-w-0 items-start justify-between gap-2">
                    <div className="min-w-0">
                      <p className="text-xs text-gray-500 dark:text-gray-400">
                        {formatTransactionDate(transaction.transactionDate)}
                      </p>
                      <p className="mt-1 break-all text-sm font-medium text-gray-900 dark:text-white">
                        {transaction.accountNumber}
                      </p>
                    </div>

                    <span className="shrink-0 rounded-full bg-gray-100 px-2 py-1 text-xs font-medium text-gray-700 dark:bg-gray-800 dark:text-gray-300">
                      {transaction.transactionType}
                    </span>
                  </div>

                  <div className="mt-3 flex min-w-0 items-center justify-between gap-2 border-t border-gray-100 pt-3 dark:border-gray-800">
                    <span
                      className={`shrink-0 rounded-full px-2 py-1 text-xs font-semibold ${statusClasses}`}
                    >
                      {transaction.transactionStatus}
                    </span>

                    <span
                      className={
                        isCredit
                          ? "text-sm font-semibold text-green-600 dark:text-green-400"
                          : isDebit
                            ? "text-sm font-semibold text-red-600 dark:text-red-400"
                            : "text-sm font-semibold text-gray-600 dark:text-gray-400"
                      }
                    >
                      {isCredit ? "+" : isDebit ? "−" : ""}
                      {Number(transaction.amount).toFixed(2)} €
                    </span>
                  </div>

                  <p className="mt-2 truncate text-xs text-gray-500 dark:text-gray-400">
                    {transaction.description || "Transaction"} · #
                    {transaction.transactionId}
                  </p>
                </button>
              );
            })}

            {transactions.length === 0 && (
              <div className="rounded-xl bg-white px-4 py-6 text-center text-sm text-gray-500 shadow dark:bg-gray-900 dark:text-gray-400">
                No transactions found.
              </div>
            )}
          </div>

          {/* Desktop transaction table */}
          <div className="hidden overflow-x-auto rounded-xl bg-white shadow dark:bg-gray-900 lg:block">
            <table className="w-full text-left text-sm">
              <thead className="border-b border-gray-200 bg-gray-50 dark:border-gray-700 dark:bg-gray-800">
                <tr>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Date
                  </th>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Account
                  </th>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Type
                  </th>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Amount
                  </th>
                  <th className="px-4 py-3 text-gray-900 dark:text-white">
                    Status
                  </th>
                </tr>
              </thead>

              <tbody>
                {sortedTransactions.map((transaction) => {
                  const { isCredit, isDebit } =
                    getTransactionDirection(transaction);
                  const statusClasses = getTransactionStatusClasses(
                    transaction.transactionStatus,
                  );

                  return (
                    <tr
                      key={transaction.transactionId}
                      onClick={() =>
                        navigate(`/transactions/${transaction.transactionId}`)
                      }
                      className="cursor-pointer border-b border-gray-100 hover:bg-gray-50 dark:border-gray-800 dark:hover:bg-gray-800"
                    >
                      <td className="whitespace-nowrap px-4 py-3 text-gray-900 dark:text-white">
                        {formatTransactionDate(transaction.transactionDate)}
                      </td>

                      <td className="whitespace-nowrap px-4 py-3 text-gray-900 dark:text-white">
                        {transaction.accountNumber}
                      </td>

                      <td className="whitespace-nowrap px-4 py-3">
                        <span className="rounded-full bg-gray-100 px-2 py-1 text-xs font-medium text-gray-700 dark:bg-gray-800 dark:text-gray-300">
                          {transaction.transactionType}
                        </span>
                      </td>

                      <td
                        className={
                          isCredit
                            ? "whitespace-nowrap px-4 py-3 font-semibold text-green-600 dark:text-green-400"
                            : isDebit
                              ? "whitespace-nowrap px-4 py-3 font-semibold text-red-600 dark:text-red-400"
                              : "whitespace-nowrap px-4 py-3 font-semibold text-gray-600 dark:text-gray-400"
                        }
                      >
                        {isCredit ? "+" : isDebit ? "−" : ""}
                        {Number(transaction.amount).toFixed(2)} €
                      </td>

                      <td className="whitespace-nowrap px-4 py-3">
                        <span
                          className={`rounded-full px-2 py-1 text-xs font-semibold ${statusClasses}`}
                        >
                          {transaction.transactionStatus}
                        </span>
                      </td>
                    </tr>
                  );
                })}

                {transactions.length === 0 && (
                  <tr>
                    <td
                      colSpan={5}
                      className="px-4 py-6 text-center text-gray-500 dark:text-gray-400"
                    >
                      No transactions found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>

        {/* Edit customer popup */}
        {showEditForm && customer && (
          <CustomerForm
            customer={customer}
            onSuccess={async () => {
              setShowEditForm(false);
              await reloadCustomer();
            }}
            onCancel={() => setShowEditForm(false)}
          />
        )}

        {/* Create account popup */}
        {showAccountForm && (
          <AccountForm
            customerId={customer.customerId}
            customerName={`${customer.firstName} ${customer.lastName}`}
            onSuccess={async () => {
              setShowAccountForm(false);
              await reloadAccountsAndTransactions();
            }}
            onCancel={() => setShowAccountForm(false)}
          />
        )}

        {/* Create transaction popup */}
        {showTransactionForm && accounts.length > 0 && (
          <TransactionForm
            accounts={accounts}
            onSuccess={async () => {
              setShowTransactionForm(false);
              await reloadAccountsAndTransactions();
            }}
            onCancel={() => setShowTransactionForm(false)}
          />
        )}

        {/* Change password popup */}
        {showPasswordForm && (
          <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
            <div className="w-full max-w-md rounded-xl bg-white p-6 shadow-xl dark:bg-gray-900">
              <h2 className="mb-6 text-2xl font-bold text-gray-900 dark:text-white">
                Change Password
              </h2>

              <div className="space-y-4">
                <div>
                  <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Current password
                  </label>
                  <input
                    type="password"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    className="w-full rounded-lg border border-gray-300 px-3 py-2 outline-none focus:border-gray-500 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                  />
                </div>

                <div>
                  <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
                    New password
                  </label>
                  <input
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    className="w-full rounded-lg border border-gray-300 px-3 py-2 outline-none focus:border-gray-500 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                  />
                </div>

                <div>
                  <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Confirm new password
                  </label>
                  <input
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    className="w-full rounded-lg border border-gray-300 px-3 py-2 outline-none focus:border-gray-500 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                  />
                </div>

                {passwordError && (
                  <div className="rounded-lg bg-red-100 px-4 py-3 text-sm font-medium text-red-700 dark:bg-red-900/30 dark:text-red-400">
                    {passwordError}
                  </div>
                )}

                {passwordSuccess && (
                  <div className="rounded-lg bg-green-100 px-4 py-3 text-sm font-medium text-green-700 dark:bg-green-900/30 dark:text-green-400">
                    {passwordSuccess}
                  </div>
                )}
              </div>

              <div className="mt-6 flex justify-end gap-3">
                <button
                  type="button"
                  onClick={() => {
                    setShowPasswordForm(false);
                    setCurrentPassword("");
                    setNewPassword("");
                    setConfirmPassword("");
                    setPasswordError("");
                    setPasswordSuccess("");
                  }}
                  className="rounded-lg border border-gray-300 px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:border-gray-700 dark:text-gray-300 dark:hover:bg-gray-800"
                >
                  Cancel
                </button>

                <button
                  type="button"
                  onClick={handleChangePassword}
                  disabled={passwordLoading}
                  className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 disabled:cursor-not-allowed disabled:opacity-50 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
                >
                  {passwordLoading ? "Updating..." : "Change Password"}
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </main>
  );
}

export default CustomerDetails;
