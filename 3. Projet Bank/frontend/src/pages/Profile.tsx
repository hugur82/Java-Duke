import { useState } from "react";
import { changePassword, updateProfile } from "../api/profileApi";
import { useAuth } from "../context/AuthContext";

function Profile() {
  const { employee } = useAuth();

  const [firstName, setFirstName] = useState(employee?.firstName ?? "");
  const [lastName, setLastName] = useState(employee?.lastName ?? "");
  const [email, setEmail] = useState(employee?.email ?? "");

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [changingPassword, setChangingPassword] = useState(false);
  const [passwordMessage, setPasswordMessage] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  if (!employee) {
    return null;
  }

  const handleSave = async () => {
    try {
      setSaving(true);
      setMessage("");
      setError("");

      const updatedEmployee = await updateProfile({
        firstName,
        lastName,
        email,
        role: employee.role,
      });

      setFirstName(updatedEmployee.firstName);
      setLastName(updatedEmployee.lastName);
      setEmail(updatedEmployee.email);

      setMessage("Profile updated successfully.");
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError("Failed to update profile.");
      }
    } finally {
      setSaving(false);
    }
  };

  const handleChangePassword = async () => {
    if (newPassword !== confirmPassword) {
      setPasswordError("New passwords do not match.");
      setPasswordMessage("");
      return;
    }

    try {
      setChangingPassword(true);
      setPasswordMessage("");
      setPasswordError("");

      await changePassword(currentPassword, newPassword);

      setCurrentPassword("");
      setNewPassword("");
      setConfirmPassword("");

      setPasswordMessage("Password changed successfully.");
    } catch (error) {
      if (error instanceof Error) {
        setPasswordError(error.message);
      } else {
        setPasswordError("Failed to change password.");
      }
    } finally {
      setChangingPassword(false);
    }
  };
  return (
    <main className="flex-1 overflow-y-auto bg-gray-50 p-8 dark:bg-gray-950">
      <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
        Profile
      </h1>

      <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
        Manage your employee profile.
      </p>

      {/* Personal Information */}
      <div className="mt-6 max-w-2xl rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-800 dark:bg-gray-900">
        {message && (
          <div className="mb-6 rounded-lg border border-green-300 bg-green-50 p-4 text-green-700">
            {message}
          </div>
        )}

        {error && (
          <div className="mb-6 rounded-lg border border-red-300 bg-red-50 p-4 text-red-700">
            {error}
          </div>
        )}

        <div className="space-y-5">
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              First name
            </label>

            <input
              value={firstName}
              onChange={(event) => setFirstName(event.target.value)}
              className="w-full rounded-lg border px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Last name
            </label>

            <input
              value={lastName}
              onChange={(event) => setLastName(event.target.value)}
              className="w-full rounded-lg border px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Email
            </label>

            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              className="w-full rounded-lg border px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          <div>
            <p className="mb-1 text-sm font-medium text-gray-700 dark:text-gray-300">
              Role
            </p>

            <p className="text-lg text-gray-900 dark:text-white">
              {employee.role}
            </p>
          </div>

          <div>
            <p className="mb-1 text-sm font-medium text-gray-700 dark:text-gray-300">
              Status
            </p>

            <p className="text-lg text-gray-900 dark:text-white">
              {employee.status}
            </p>
          </div>

          <button
            type="button"
            onClick={handleSave}
            disabled={saving}
            className="rounded-lg bg-black px-4 py-2 text-white disabled:opacity-50"
          >
            {saving ? "Saving..." : "Save"}
          </button>
        </div>
      </div>

      {/* Change Password */}
      <div className="mt-6 max-w-2xl rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-800 dark:bg-gray-900">
        <h2 className="text-lg font-semibold text-gray-900 dark:text-white">
          Change Password
        </h2>

        {passwordMessage && (
          <div className="mt-4 rounded-lg border border-green-300 bg-green-50 p-4 text-green-700">
            {passwordMessage}
          </div>
        )}

        {passwordError && (
          <div className="mt-4 rounded-lg border border-red-300 bg-red-50 p-4 text-red-700">
            {passwordError}
          </div>
        )}

        <div className="mt-6 space-y-5">
          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Current password
            </label>

            <input
              type="password"
              value={currentPassword}
              onChange={(event) => setCurrentPassword(event.target.value)}
              className="w-full rounded-lg border px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              New password
            </label>

            <input
              type="password"
              value={newPassword}
              onChange={(event) => setNewPassword(event.target.value)}
              className="w-full rounded-lg border px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-gray-700 dark:text-gray-300">
              Confirm new password
            </label>

            <input
              type="password"
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
              className="w-full rounded-lg border px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
            />
          </div>

          <button
            type="button"
            onClick={handleChangePassword}
            disabled={changingPassword}
            className="rounded-lg bg-black px-4 py-2 text-white disabled:opacity-50"
          >
            {changingPassword ? "Changing..." : "Change password"}
          </button>
        </div>
      </div>
    </main>
  );
}

export default Profile;
