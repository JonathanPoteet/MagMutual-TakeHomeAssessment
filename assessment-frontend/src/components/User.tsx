import React, { useEffect, useMemo, useState } from 'react';
import { userService, User } from '../services/UserService';
import CurrentTrends from './CurrentTrends';

const getSeasonForDate = (dateValue: string): 'Spring' | 'Summer' | 'Fall' | 'Winter' => {
  const parsedDate = new Date(dateValue);

  if (Number.isNaN(parsedDate.getTime())) {
    return 'Spring';
  }

  const month = parsedDate.getMonth() + 1;

  if (month >= 3 && month <= 5) return 'Spring';
  if (month >= 6 && month <= 8) return 'Summer';
  if (month >= 9 && month <= 11) return 'Fall';
  return 'Winter';
};

export default function UserPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedUserId, setSelectedUserId] = useState<number>(0);
  const [selectedSection, setSelectedSection] = useState<'list' | 'trends'>('list');
  const [sortKey, setSortKey] = useState<keyof User>('lastname');
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');
  const [users, setUsers] = useState<User[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [cardImage, setCardImage] = useState<string | null>(null);
  const pageSize = 10;

  const getAllUsers = async () => {
    try {
      const fetchedUsers = await userService.getUsers();
      setUsers(fetchedUsers);

    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  useEffect(() => {
    getAllUsers();
  }, []);

  useEffect(() => {
    if (selectedUserId === 0) {
      setSelectedUser(null);
      setCardImage(null);
      return;
    }

    const fetchSelectedUser = async () => {
      try {
        const user = await userService.getUserById(selectedUserId);
        const season = getSeasonForDate(user.dateCreated);
        setCardImage(`/Placeholder/${season}.jpg`);
        setSelectedUser(user);
      } catch (error) {
        console.error('Error fetching selected user:', error);
      }
    };

    fetchSelectedUser();
  }, [selectedUserId]);


  useEffect(() => {
    setCurrentPage(1);
  }, [searchTerm, sortKey, sortDirection, users.length]);
  
  
  const filteredUsers = useMemo(() => {
    const term = searchTerm.trim().toLowerCase();
    
    if (!term) {
      return users;
    }

    return users.filter((user) =>
      `${user.firstname} ${user.lastname}`.toLowerCase().includes(term) ||
      user.email.toLowerCase().includes(term) ||
      user.city.toLowerCase().includes(term) ||
      user.profession.toLowerCase().includes(term)
    );
  }, [searchTerm, users]);

  const sortedUsers = useMemo(() => {
    const data = [...filteredUsers];

    data.sort((a, b) => {
      const left = a[sortKey];
      const right = b[sortKey];

      if (left < right) {
        return sortDirection === 'asc' ? -1 : 1;
      }

      if (left > right) {
        return sortDirection === 'asc' ? 1 : -1;
      }

      return 0;
    });

    return data;
  }, [filteredUsers, sortDirection, sortKey]);

  const totalPages = Math.max(1, Math.ceil(sortedUsers.length / pageSize));

  const paginatedUsers = useMemo(() => {
    const startIndex = (currentPage - 1) * pageSize;
    setSelectedUserId(sortedUsers[startIndex]?.id || 0);
    return sortedUsers.slice(startIndex, startIndex + pageSize);
  }, [sortedUsers, currentPage]);


  const handleSort = (key: keyof User) => {
    if (sortKey === key) {
      setSortDirection((current) => (current === 'asc' ? 'desc' : 'asc'));
      return;
    }
    setSortKey(key);
    setSortDirection('asc');
  };



  return users.length > 0 ? (
    // this section could be its own component, but for simplicity, it's kept here
    <div className="dashboard-shell">
      <div className="navigation-header">
        <div className="navigation-buttons">
          <button
            type="button"
            className={`nav-button ${selectedSection === 'list' ? 'active' : ''}`}
            onClick={() => setSelectedSection('list')}
          >
            User List
          </button>
          <button
            type="button"
            className={`nav-button ${selectedSection === 'trends' ? 'active' : ''}`}
            onClick={() => setSelectedSection('trends')}
          >
            Signup Trends
          </button>
        </div>
      </div>

      {selectedSection === 'list' ? (
        <section className="panel panel-list">
          <div className="panel-header">
            <h2>User List</h2>
            <span>{filteredUsers.length} total</span>
          </div>

          <label className="search-field">
            <input
              type="text"
              value={searchTerm}
              onChange={(event) => setSearchTerm(event.target.value)}
              placeholder="Search by name, email, city, or role"
            />
          </label>

          <div className="user-table-wrapper">
            <table className="user-table">
              <thead>
                <tr>
                  <th>
                    <button type="button" className="sort-button" onClick={() => handleSort('firstname')}>
                      First Name
                    </button>
                  </th>
                  <th>
                    <button type="button" className="sort-button" onClick={() => handleSort('lastname')}>
                      Last Name
                    </button>
                  </th>
                  <th>
                    <button type="button" className="sort-button" onClick={() => handleSort('email')}>
                      Email
                    </button>
                  </th>
                  <th>
                    <button type="button" className="sort-button" onClick={() => handleSort('city')}>
                      City
                    </button>
                  </th>
                  <th>
                    <button type="button" className="sort-button" onClick={() => handleSort('profession')}>
                      Profession
                    </button>
                  </th>
                </tr>
              </thead>
              <tbody>
                {paginatedUsers.map((user) => (
                  <tr
                    key={user.id}
                    className={selectedUser?.id === user.id ? 'selected-row' : ''}
                    onClick={() => setSelectedUserId(user.id)}
                  >
                    <td>{user.firstname}</td>
                    <td>{user.lastname}</td>
                    <td>{user.email}</td>
                    <td>{user.city}</td>
                    <td>{user.profession}</td>
                    <td>
                      <button
                        type="button"
                        className="delete-button"
                        aria-label={`Delete ${user.firstname} ${user.lastname}`}
                        onClick={(event) => {
                          event.stopPropagation();
                          console.log('Delete user', user.id);
                        }}
                      >
                        <span className="material-symbols-outlined" aria-hidden="true">delete</span>
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="pagination-controls">
            <button
              type="button"
              onClick={() => setCurrentPage((page) => Math.max(1, page - 1))}
              disabled={currentPage === 1}
            >
              Previous
            </button>
            <span>
              Page {currentPage} of {totalPages}
            </span>
            <button
              type="button"
              onClick={() => setCurrentPage((page) => Math.min(totalPages, page + 1))}
              disabled={currentPage === totalPages}
            >
              Next
            </button>
          </div>

          {selectedUser && (
            <div className="user-card">
              <img
                src={cardImage ?? '/Placeholder/Spring.jpg'}
                alt={`${selectedUser.firstname} ${selectedUser.lastname} seasonal avatar`}
                className="user-avatar"
              />
              <h3>
                {selectedUser.firstname} {selectedUser.lastname}
              </h3>
              <p>{selectedUser.email}</p>
              <div className="user-meta">
                <span>{selectedUser.country}</span>
                <span>{selectedUser.city}</span>
                <span>{selectedUser.profession}</span>
              </div>
              <small>Joined: {selectedUser.dateCreated}</small>
            </div>
          )}
        </section>
      ) : (<CurrentTrends />)}
    </div>
  ) : (
    <div className="loading-message">
      {/* The loading message could be its own component */}
      <p>Loading users...</p>
    </div>
  );
}
