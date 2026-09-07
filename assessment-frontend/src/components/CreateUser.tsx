import React, { useRef, useState } from 'react';
import { userService } from '../services/UserService';

type CreateUserProps = {
  onUserCreated?: () => void;
};

const CreateUser = ({ onUserCreated }: CreateUserProps) => {
  const dialogRef = useRef<HTMLDialogElement | null>(null);
  const [formData, setFormData] = useState({
    firstname: '',
    lastname: '',
    email: '',
    city: '',
    country: '',
    profession: '',
  });

  const openDialog = () => {
    dialogRef.current?.showModal();
  };

  const closeDialog = () => {
    dialogRef.current?.close();
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFormData((current) => ({
      ...current,
      [name]: value,
    }));
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const payload = {
        ...formData,
        dateCreated: new Date().toISOString().slice(0, 10),
      };

      await userService.createUser(payload);
      closeDialog();
      setFormData({
        firstname: '',
        lastname: '',
        email: '',
        city: '',
        country: '',
        profession: '',
      });
      onUserCreated?.();
    } catch (error) {
      console.error('Error creating user:', error);
      alert('Unable to create user. Please try again.');
    }
  };

  return (
    <div>
      <button type="button" className="nav-button" onClick={openDialog}>
        Create User
      </button>

      <dialog ref={dialogRef} className="create-user-dialog">
        <form method="dialog" className="create-user-form" onSubmit={handleSubmit}>
          <div className="create-user-row">
            <label>
              <span className="field-label">First Name</span>
            </label>
            <input type="text" name="firstname" value={formData.firstname} onChange={handleChange} required />
            <span className="create-user-required">*</span>
          </div>

          <div className="create-user-row">
            <label>
              <span className="field-label">Last Name</span>
            </label>
            <input type="text" name="lastname" value={formData.lastname} onChange={handleChange} required />
            <span className="create-user-required">*</span>
          </div>

          <div className="create-user-row">
            <label>
              <span className="field-label">Email</span>
            </label>
            <input type="email" name="email" value={formData.email} onChange={handleChange} required />
            <span className="create-user-required">*</span>
          </div>

          <div className="create-user-row">
            <label>
              <span className="field-label">City</span>
            </label>
            <input type="text" name="city" value={formData.city} onChange={handleChange} required />
            <span className="create-user-required">*</span>
          </div>

          <div className="create-user-row">
            <label>
              <span className="field-label">Country</span>
            </label>
            <input type="text" name="country" value={formData.country} onChange={handleChange} required />
            <span className="create-user-required">*</span>
          </div>

          <div className="create-user-row">
            <label>
              <span className="field-label">Profession</span>
            </label>
            <input type="text" name="profession" value={formData.profession} onChange={handleChange} required />
            <span className="create-user-required">*</span>
          </div>

          <div className="create-user-actions">
            <button type="submit">Create User</button>
            <button type="button" onClick={closeDialog}>Cancel</button>
          </div>
        </form>
      </dialog>
    </div>
  );
};

export default CreateUser;
