import React from 'react';

type User = {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
};

export default function UserPage() {
  const users: User[] = [
    { id: 1, firstName: 'John', lastName: 'Doe', email: 'john.doe@example.com' },
    { id: 2, firstName: 'Jane', lastName: 'Smith', email: 'jane.smith@example.com' },
  ];

  return (
    <div style={{ padding: '2rem', maxWidth: '800px', margin: '0 auto' }}>
      <h1>User Directory</h1>

      {users.map((user) => (
        <div
          key={user.id}
        >
          <h2 style={{ margin: '0 0 0.5rem' }}>
            {user.firstName} {user.lastName}
          </h2>
          <p style={{ margin: 0 }}>{user.email}</p>
        </div>
      ))}
    </div>
  );
}
