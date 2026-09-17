import { useEffect, useState } from "react";
import { getCustomers, type Customer } from "../api/customerApi";

interface Customer {
  customerId: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  city: string;
  status: string;
}

function Customers() {
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    getCustomers()
      .then((data) => {
        setCustomers(data);
      })
      .catch(() => {
        setError("Unable to load customers.");
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
            Customers
          </h1>

          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
            Manage bank customers.
          </p>
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
                      className="hover:bg-gray-50 dark:hover:bg-gray-800"
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

                      <td className="px-6 py-4 text-sm">{customer.status}</td>
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
      </div>
    </main>
  );
}

export default Customers;
