import { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";
import { getCustomers, type Customer } from "../api/customerApi";
import CustomerForm from "../components/customer/CustomerForm";

function Customers() {
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    let cancelled = false;

    getCustomers()
      .then((data) => {
        if (!cancelled) {
          setCustomers(data);
        }
      })
      .catch(() => {
        if (!cancelled) {
          setError("Unable to load customers.");
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
  }, []);

  const loadCustomers = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getCustomers();

      setCustomers(data);
    } catch {
      setError("Unable to load customers.");
    } finally {
      setLoading(false);
    }
  };

  const handleCustomerCreated = async () => {
    setShowForm(false);
    await loadCustomers();
  };

  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <div className="mx-auto max-w-7xl">
        {/* Header */}
        <div className="mb-8 flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
              Customers
            </h1>

            <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
              Manage bank customers.
            </p>
          </div>

          <button
            type="button"
            onClick={() => setShowForm(true)}
            className="rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
          >
            + Add Customer
          </button>
        </div>

        {loading && (
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Loading customers...
          </p>
        )}

        {error && (
          <p className="text-sm font-medium text-red-600 dark:text-red-400">
            {error}
          </p>
        )}

        {!loading && !error && (
          <div className="overflow-x-auto rounded-xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900">
            <table className="w-full text-left">
              <thead className="border-b border-gray-200 bg-gray-50 dark:border-gray-800 dark:bg-gray-800">
                <tr>
                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    ID
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Name
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Email
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Phone
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    City
                  </th>

                  <th className="px-6 py-3 text-xs font-semibold uppercase text-gray-500 dark:text-gray-400">
                    Status
                  </th>
                </tr>
              </thead>

              <tbody className="divide-y divide-gray-200 dark:divide-gray-800">
                {customers.length > 0 ? (
                  customers.map((customer) => (
                    <tr
                      key={customer.customerId}
                      onClick={() =>
                        navigate(`/customers/${customer.customerId}`)
                      }
                      className="cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800"
                    >
                      <td className="px-6 py-4 text-sm text-gray-900 dark:text-white">
                        #{customer.customerId}
                      </td>

                      <td className="px-6 py-4 text-sm font-medium text-gray-900 dark:text-white">
                        {customer.firstName} {customer.lastName}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {customer.email}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {customer.phone}
                      </td>

                      <td className="px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
                        {customer.city}
                      </td>

                      <td className="px-6 py-4 text-sm">
                        <span
                          className={
                            customer.status === "ACTIVE"
                              ? "rounded-full bg-green-100 px-3 py-1 text-xs font-semibold text-green-700 dark:bg-green-900/30 dark:text-green-400"
                              : customer.status === "SUSPENDED"
                                ? "rounded-full bg-red-100 px-3 py-1 text-xs font-semibold text-red-700 dark:bg-red-900/30 dark:text-red-400"
                                : "rounded-full bg-gray-100 px-3 py-1 text-xs font-semibold text-gray-700 dark:bg-gray-800 dark:text-gray-300"
                          }
                        >
                          {customer.status}
                        </span>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td
                      colSpan={6}
                      className="px-6 py-10 text-center text-sm font-semibold text-gray-500 dark:text-gray-400"
                    >
                      No customers found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        )}

        {showForm && (
          <CustomerForm
            onSuccess={handleCustomerCreated}
            onCancel={() => setShowForm(false)}
          />
        )}
      </div>
    </main>
  );
}

export default Customers;
