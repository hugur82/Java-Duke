import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  createEmployee,
  getEmployees,
  type Employee,
  type EmployeeCreateRequest,
  type EmployeeRole,
} from "../api/employeeApi";

function Employees() {
  const navigate = useNavigate();

  const [employees, setEmployees] = useState<Employee[]>([]);
  const [firstNameSearch, setFirstNameSearch] = useState("");
  const [lastNameSearch, setLastNameSearch] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showCreateForm, setShowCreateForm] = useState(false);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState<EmployeeRole>("EMPLOYEE");

  const [creating, setCreating] = useState(false);
  const [createError, setCreateError] = useState("");

  useEffect(() => {
    async function loadEmployees() {
      try {
        setLoading(true);
        setError("");

        const data = await getEmployees();
        setEmployees(data);
      } catch (error) {
        if (error instanceof Error) {
          setError(error.message);
        } else {
          setError("Failed to load employees.");
        }
      } finally {
        setLoading(false);
      }
    }

    loadEmployees();
  }, []);

  const filteredEmployees = employees.filter((employee) => {
    const firstNameMatch = employee.firstName
      .toLowerCase()
      .includes(firstNameSearch.toLowerCase());

    const lastNameMatch = employee.lastName
      .toLowerCase()
      .includes(lastNameSearch.toLowerCase());

    return firstNameMatch && lastNameMatch;
  });

  const handleCreateEmployee = async () => {
    if (
      !firstName.trim() ||
      !lastName.trim() ||
      !email.trim() ||
      !password.trim()
    ) {
      setCreateError("Please fill in all required fields.");
      return;
    }

    const employeeData: EmployeeCreateRequest = {
      firstName: firstName.trim(),
      lastName: lastName.trim(),
      email: email.trim(),
      password,
      role,
    };

    try {
      setCreating(true);
      setCreateError("");

      const newEmployee = await createEmployee(employeeData);

      setEmployees((current) => [...current, newEmployee]);

      setFirstName("");
      setLastName("");
      setEmail("");
      setPassword("");
      setRole("EMPLOYEE");

      setShowCreateForm(false);
    } catch (error) {
      if (error instanceof Error) {
        setCreateError(error.message);
      } else {
        setCreateError("Failed to create employee.");
      }
    } finally {
      setCreating(false);
    }
  };

  const handleCloseCreateForm = () => {
    if (creating) {
      return;
    }

    setShowCreateForm(false);
    setCreateError("");

    setFirstName("");
    setLastName("");
    setEmail("");
    setPassword("");
    setRole("EMPLOYEE");
  };

  if (loading) {
    return (
      <main className="flex-1 overflow-auto p-6">
        <p className="text-gray-600 dark:text-gray-400">Loading...</p>
      </main>
    );
  }

  if (error) {
    return (
      <main className="flex-1 overflow-auto p-6">
        <p className="text-red-600">{error}</p>
      </main>
    );
  }

  return (
    <main className="relative min-w-0 flex-1 overflow-auto p-4 sm:p-6">
      <div className="mb-6 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Employees
          </h1>
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Manage employees
          </p>
        </div>

        <button
          type="button"
          onClick={() => {
            setCreateError("");
            setShowCreateForm(true);
          }}
          className="self-start rounded-lg bg-black px-4 py-2 text-sm font-medium text-white hover:opacity-90 dark:bg-white dark:text-black"
        >
          + New Employee
        </button>
      </div>

      {/* Search */}
      <div className="mb-6 grid grid-cols-1 gap-4 md:grid-cols-2">
        <input
          type="text"
          aria-label="Search by first name"
          placeholder="Search by first name"
          value={firstNameSearch}
          onChange={(event) => setFirstNameSearch(event.target.value)}
          className="min-w-0 rounded-lg border border-gray-300 bg-white px-4 py-2 text-sm text-gray-900 outline-none focus:ring-2 dark:border-gray-700 dark:bg-gray-900 dark:text-white"
        />

        <input
          type="text"
          aria-label="Search by last name"
          placeholder="Search by last name"
          value={lastNameSearch}
          onChange={(event) => setLastNameSearch(event.target.value)}
          className="min-w-0 rounded-lg border border-gray-300 bg-white px-4 py-2 text-sm text-gray-900 outline-none focus:ring-2 dark:border-gray-700 dark:bg-gray-900 dark:text-white"
        />
      </div>

      {/* Mobile cards */}
      <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 md:hidden">
        {filteredEmployees.map((employee) => (
          <article
            key={employee.employeeId}
            onClick={() => navigate(`/employees/${employee.employeeId}`)}
            className="min-w-0 rounded-xl border border-gray-200 bg-white p-4 shadow-sm dark:border-gray-800 dark:bg-gray-900"
          >
            <div className="flex min-w-0 items-start justify-between gap-3">
              <div className="min-w-0">
                <h2 className="break-words font-semibold text-gray-900 dark:text-white">
                  {employee.firstName} {employee.lastName}
                </h2>
                <p className="mt-1 break-all text-sm text-gray-600 dark:text-gray-400">
                  {employee.email}
                </p>
              </div>

              <span
                className={
                  employee.role === "ADMIN"
                    ? "shrink-0 rounded-full bg-purple-100 px-2.5 py-1 text-xs font-semibold text-purple-700 dark:bg-purple-900/30 dark:text-purple-400"
                    : "shrink-0 rounded-full bg-blue-100 px-2.5 py-1 text-xs font-semibold text-blue-700 dark:bg-blue-900/30 dark:text-blue-400"
                }
              >
                {employee.role}
              </span>
            </div>

            <div className="mt-4 flex items-center justify-between gap-3 border-t border-gray-100 pt-3 dark:border-gray-800">
              <span className="text-sm text-gray-500 dark:text-gray-400">
                Status
              </span>
              <span className="break-words text-right text-sm font-medium text-gray-900 dark:text-white">
                {employee.status}
              </span>
            </div>
          </article>
        ))}

        {filteredEmployees.length === 0 && (
          <div className="col-span-full rounded-xl border border-gray-200 bg-white p-6 text-center text-sm text-gray-500 dark:border-gray-800 dark:bg-gray-900 dark:text-gray-400">
            No employees found.
          </div>
        )}
      </div>

      {/* Desktop table */}
      <div className="hidden overflow-x-auto rounded-lg border border-gray-200 bg-white dark:border-gray-800 dark:bg-gray-900 md:block">
        <table className="w-full text-left">
          <thead className="border-b border-gray-200 dark:border-gray-800">
            <tr>
              <th className="px-6 py-4 text-sm font-semibold text-gray-900 dark:text-white">
                First name
              </th>
              <th className="px-6 py-4 text-sm font-semibold text-gray-900 dark:text-white">
                Last name
              </th>
              <th className="px-6 py-4 text-sm font-semibold text-gray-900 dark:text-white">
                Email
              </th>
              <th className="px-6 py-4 text-sm font-semibold text-gray-900 dark:text-white">
                Role
              </th>
              <th className="px-6 py-4 text-sm font-semibold text-gray-900 dark:text-white">
                Status
              </th>
            </tr>
          </thead>

          <tbody>
            {filteredEmployees.map((employee) => (
              <tr
                key={employee.employeeId}
                onClick={() => navigate(`/employees/${employee.employeeId}`)}
                className="border-b border-gray-200 last:border-b-0 hover:bg-gray-50 dark:border-gray-800 dark:hover:bg-gray-800"
              >
                <td className="px-6 py-4 text-gray-900 dark:text-white">
                  {employee.firstName}
                </td>
                <td className="px-6 py-4 text-gray-900 dark:text-white">
                  {employee.lastName}
                </td>
                <td className="px-6 py-4 text-gray-700 dark:text-gray-300">
                  {employee.email}
                </td>
                <td className="px-6 py-4 text-gray-700 dark:text-gray-300">
                  {employee.role}
                </td>
                <td className="px-6 py-4 text-gray-700 dark:text-gray-300">
                  {employee.status}
                </td>
              </tr>
            ))}

            {filteredEmployees.length === 0 && (
              <tr>
                <td
                  colSpan={5}
                  className="px-6 py-10 text-center text-sm text-gray-500 dark:text-gray-400"
                >
                  No employees found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Create employee modal */}
      {showCreateForm && (
        <div className="fixed inset-0 z-50 flex items-center justify-center overflow-y-auto bg-black/50 p-4">
          <div className="my-auto w-full max-w-lg rounded-lg bg-white p-4 shadow-xl dark:bg-gray-900 sm:p-6">
            <div className="mb-6 flex items-center justify-between gap-3">
              <h2 className="text-xl font-bold text-gray-900 dark:text-white">
                New Employee
              </h2>

              <button
                type="button"
                onClick={handleCloseCreateForm}
                disabled={creating}
                aria-label="Close form"
                className="text-xl text-gray-500 hover:text-gray-900 disabled:opacity-50 dark:hover:text-white"
              >
                ✕
              </button>
            </div>

            {createError && (
              <div className="mb-4 rounded-lg border border-red-300 bg-red-50 p-3 text-sm text-red-700 dark:border-red-900 dark:bg-red-950/30 dark:text-red-400">
                {createError}
              </div>
            )}

            <div className="space-y-4">
              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  First name
                </label>
                <input
                  type="text"
                  value={firstName}
                  required
                  onChange={(event) => setFirstName(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Last name
                </label>
                <input
                  type="text"
                  value={lastName}
                  required
                  onChange={(event) => setLastName(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Email
                </label>
                <input
                  type="email"
                  value={email}
                  required
                  onChange={(event) => setEmail(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Password
                </label>
                <input
                  type="password"
                  value={password}
                  required
                  onChange={(event) => setPassword(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Role
                </label>
                <select
                  value={role}
                  onChange={(event) =>
                    setRole(event.target.value as EmployeeRole)
                  }
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                >
                  <option value="EMPLOYEE">EMPLOYEE</option>
                  <option value="ADMIN">ADMIN</option>
                </select>
              </div>
            </div>

            <div className="mt-6 flex flex-col-reverse justify-end gap-3 sm:flex-row">
              <button
                type="button"
                onClick={handleCloseCreateForm}
                disabled={creating}
                className="rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 disabled:opacity-50 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
              >
                Cancel
              </button>

              <button
                type="button"
                onClick={handleCreateEmployee}
                disabled={creating}
                className="rounded-lg bg-black px-4 py-2 text-white hover:bg-gray-800 disabled:opacity-50 dark:bg-white dark:text-black dark:hover:bg-gray-200"
              >
                {creating ? "Creating..." : "Create Employee"}
              </button>
            </div>
          </div>
        </div>
      )}
    </main>
  );
}

export default Employees;
