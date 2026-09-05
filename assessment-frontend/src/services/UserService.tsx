export type User = {
  id: number;
  firstname: string;
  lastname: string;
  email: string;
  profession: string;
  dateCreated: string;
  country: string;
  city: string;
};

export type RecentSignup = {
  dateCreated: string;
  city: string;
  profession: string;
};

const API_BASE_URL = process.env.REACT_APP_API_URL || '';

async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {}),
    },
    ...options,
  });

  if (response.status === 204) {
    return undefined as T;
  }

  const text = await response.text();

  if (!response.ok) {
    throw new Error(text || `Request failed: ${response.status}`);
  }

  if (!text) {
    return undefined as T;
  }

  try {
    return JSON.parse(text) as T;
  } catch (error) {
    throw new Error(`Unexpected response from ${endpoint}: ${text.slice(0, 200)}`);
  }
}

export const userService = {
  async getUsers(): Promise<User[]> {
    return request<User[]>('/api/users');
  },

  async getUserById(id: number): Promise<User> {
    return request<User>(`/api/users/${id}`);
  },

  async createUser(user: Partial<User>): Promise<User> {
    return request<User>('/api/users', {
      method: 'POST',
      body: JSON.stringify(user),
    });
  },

  async deleteUser(id: number): Promise<void> {
    return request<void>(`/api/users/${id}`, {
      method: 'DELETE',
    });
  },

  async getTrends(): Promise<RecentSignup[]> {
    return request<RecentSignup[]>('/api/users/trends');
  },
};

export default userService;
