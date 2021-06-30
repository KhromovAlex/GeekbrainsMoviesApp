package com.example.geekbrainsmoviesapp.presentation.view

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geekbrainsmoviesapp.R
import com.example.geekbrainsmoviesapp.databinding.FragmentItemListBinding
import com.example.geekbrainsmoviesapp.model.Contact
import com.example.geekbrainsmoviesapp.presentation.adapter.MyContactRecyclerViewAdapter


const val REQUEST_CODE = 42

class ContactFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterContact: MyContactRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterContact = MyContactRecyclerViewAdapter(getString(R.string.empty_phone))
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterContact
        }

        checkPermission()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    getContacts()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setMessage(getString(R.string.no_permission_contacts))
                            .setNegativeButton(getString(R.string.close)) { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermission() {
        context?.let {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    private fun getContacts() {
        context?.let {
            val list = mutableListOf<Contact>()
            val contentResolver: ContentResolver = it.contentResolver
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val id =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                        val phones: Cursor? = contentResolver.query(
                            Phone.CONTENT_URI,
                            null,
                            Phone.CONTACT_ID + " = " + id,
                            null,
                            null
                        )
                        var phone = ""
                        phones?.let {
                            while (phones.moveToNext()) {
                                phone =
                                    if (phone == "") phones.getString(phones.getColumnIndex(Phone.NUMBER)) else "$phone, ${
                                        phones.getString(phones.getColumnIndex(Phone.NUMBER))
                                    }"
                            }
                        }

                        phones?.close()


                        list.add(Contact(id = id, name, phone))
                    }
                }
                adapterContact.setData(list)
            }
            cursorWithContacts?.close()
        }
    }
}
