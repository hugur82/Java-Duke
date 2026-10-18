import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getCustomers, type Customer } from "../api/customerApi";
import CustomerForm from "../components/customer/CustomerForm";

function Customers() {
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);

  // Search filters
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [city, setCity] = useState("");

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

  // Filter customers by first name, last name and city
  const filteredCustomers = customers.filter((customer) => {
    const matchesFirstName = customer.firstName
      .toLowerCase()
      .includes(firstName.trim().toLowerCase());

    const matchesLastName = customer.lastName
      .toLowerCase()
      .includes(lastName.trim().toLowerCase());

    const matchesCity = customer.city
      .toLowerCase()
      .includes(city.trim().toLowerCase());

    return matchesFirstName && matchesLastName && matchesCity;
  });

  return (
    <main className="min-w-0 flex-1 bg-gray-50 p-4 dark:bg-gray-950 sm:p-6 lg:p-8">
      <div className="mx-auto max-w-7xl">
        {/* Header */}
        <div className="mb-6 flex flex-col gap-4 sm:mb-8 sm:flex-row sm:items-center sm:justify-between">
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
            className="self-start rounded-lg bg-gray-900 px-4 py-2 text-sm font-medium text-white hover:bg-gray-700 sm:self-auto dark:bg-white dark:text-gray-900 dark:hover:bg-gray-200"
          >
            + Add Customer
          </button>
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
                onChange={(event) => setFirstName(event.target.value)}
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
                onChange={(event) => setLastName(event.target.value)}
                placeholder="Search by last name"
                className="w-full rounded-lg border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 outline-none transition focus:border-gray-500 focus:ring-2 focus:ring-gray-200 dark:border-gray-700 dark:bg-gray-800 dark:text-white dark:focus:border-gray-500 dark:focus:ring-gray-700"
              />
            </div>

            <div>
              <label
                htmlFor="city"
                className="mb-2 block text-sm font-medium text-gray-700 dark:text-gray-300"
              >
                City
              </label>

              <input
                id="city"
                type="text"
                value={city}
                onChange={(event) => setCity(event.target.value)}
                placeholder="Search by city"
                className="w-full rounded-lg border border-gray-300 bg-white px-4 py-2.5 text-sm text-gray-900 outline-none transition focus:border-gray-500 focus:ring-2 focus:ring-gray-200 dark:border-gray-700 dark:bg-gray-800 dark:text-white dark:focus:border-gray-500 dark:focus:ring-gray-700"
              />
            </div>
          </div>
        </section>

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
          <>
            {/* Results count */}
            <p className="mb-4 text-sm text-gray-500 dark:text-gray-400">
              {filteredCustomers.length} customer
              {filteredCustomers.length !== 1 ? "s" : ""} found
            </p>

            {/* Mobile: customer cards */}
            <div className="space-y-3 lg:hidden">
              {filteredCustomers.length > 0 ? (
                filteredCustomers.map((customer) => (
                  <button
                    key={customer.customerId}
                    type="button"
                    onClick={() =>
                      navigate(`/customers/${customer.customerId}`)
                    }
                    className="w-full rounded-xl border border-gray-200 bg-white p-4 text-left shadow-sm transition-colors hover:bg-gray-50 dark:border-gray-800 dark:bg-gray-900 dark:hover:bg-gray-800"
                  >
                    <div className="flex items-start justify-between gap-3">
                      <div className="min-w-0">
                        <h2 className="truncate font-semibold text-gray-900 dark:text-white">
                          {customer.firstName} {customer.lastName}
                        </h2>

                        <p className="mt-1 text-xs text-gray-500 dark:text-gray-400">
                          Customer #{customer.customerId}
                        </p>
                      </div>

                      <span
                        className={`shrink-0 rounded-full px-2.5 py-1 text-xs font-semibold ${
                          customer.status === "ACTIVE"
                            ? "bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400"
                            : customer.status === "SUSPENDED"
                              ? "bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400"
                              : "bg-gray-100 text-gray-700 dark:bg-gray-800 dark:text-gray-300"
                        }`}
                      >
                        {customer.status}
                      </span>
                    </div>

                    <div className="mt-4 space-y-2 text-sm">
                      <p className="break-all text-gray-600 dark:text-gray-300">
                        <span className="text-gray-500 dark:text-gray-400">
                          Email:{" "}
                        </span>
                        {customer.email}
                      </p>

                      <p className="text-gray-600 dark:text-gray-300">
                        <span className="text-gray-500 dark:text-gray-400">
                          Phone:{" "}
                        </span>
                        {customer.phone}
                      </p>

                      <p className="text-gray-600 dark:text-gray-300">
                        <span className="text-gray-500 dark:text-gray-400">
                          City:{" "}
                        </span>
                        {customer.city}
                      </p>
                    </div>

                    <div className="mt-4 border-t border-gray-100 pt-3 text-sm font-medium text-gray-700 dark:border-gray-800 dark:text-gray-200">
                      View customer details →
                    </div>
                  </button>
                ))
              ) : (
                <p className="rounded-xl border border-gray-200 bg-white px-4 py-8 text-center text-sm text-gray-500 dark:border-gray-800 dark:bg-gray-900 dark:text-gray-400">
                  No customers found.
                </p>
              )}
            </div>

            {/* Desktop: existing table */}
            <div className="hidden overflow-x-auto rounded-xl border border-gray-200 bg-white shadow-sm dark:border-gray-800 dark:bg-gray-900 lg:block">
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
                  {filteredCustomers.length > 0 ? (
                    filteredCustomers.map((customer) => (
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

                        <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-600 dark:text-gray-300">
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
          </>
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
