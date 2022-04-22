package com.sun.jna.platform.win32;

import com.sun.jna.ptr.*;
import com.sun.jna.*;
import java.util.*;

public abstract class Advapi32Util
{
    public static String getUserName() {
        char[] array = new char[128];
        final IntByReference intByReference = new IntByReference(array.length);
        boolean b = Advapi32.INSTANCE.GetUserNameW(array, intByReference);
        if (!b) {
            switch (Kernel32.INSTANCE.GetLastError()) {
                case 122: {
                    array = new char[intByReference.getValue()];
                    b = Advapi32.INSTANCE.GetUserNameW(array, intByReference);
                    break;
                }
                default: {
                    throw new Win32Exception(Native.getLastError());
                }
            }
        }
        if (!b) {
            throw new Win32Exception(Native.getLastError());
        }
        return Native.toString(array);
    }
    
    public static Account getAccountByName(final String s) {
        return getAccountByName(null, s);
    }
    
    public static Account getAccountByName(final String s, final String s2) {
        final IntByReference intByReference = new IntByReference(0);
        final IntByReference intByReference2 = new IntByReference(0);
        final PointerByReference pointerByReference = new PointerByReference();
        if (Advapi32.INSTANCE.LookupAccountName(s, s2, null, intByReference, null, intByReference2, pointerByReference)) {
            throw new RuntimeException("LookupAccountNameW was expected to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        final int getLastError = Kernel32.INSTANCE.GetLastError();
        if (intByReference.getValue() == 0 || getLastError != 122) {
            throw new Win32Exception(getLastError);
        }
        final WinNT.PSID psid = new WinNT.PSID(new Memory(intByReference.getValue()));
        final char[] array = new char[intByReference2.getValue() + 1];
        if (!Advapi32.INSTANCE.LookupAccountName(s, s2, psid, intByReference, array, intByReference2, pointerByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final Account account = new Account();
        account.accountType = pointerByReference.getPointer().getInt(0L);
        account.name = s2;
        final String[] split = s2.split("\\\\", 2);
        final String[] split2 = s2.split("@", 2);
        if (split.length == 2) {
            account.name = split[1];
        }
        else if (split2.length == 2) {
            account.name = split2[0];
        }
        else {
            account.name = s2;
        }
        if (intByReference2.getValue() > 0) {
            account.domain = Native.toString(array);
            account.fqn = account.domain + "\\" + account.name;
        }
        else {
            account.fqn = account.name;
        }
        account.sid = psid.getBytes();
        account.sidString = convertSidToStringSid(new WinNT.PSID(account.sid));
        return account;
    }
    
    public static Account getAccountBySid(final WinNT.PSID psid) {
        return getAccountBySid(null, psid);
    }
    
    public static Account getAccountBySid(final String s, final WinNT.PSID psid) {
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final PointerByReference pointerByReference = new PointerByReference();
        if (Advapi32.INSTANCE.LookupAccountSid(null, psid, null, intByReference, null, intByReference2, pointerByReference)) {
            throw new RuntimeException("LookupAccountSidW was expected to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        final int getLastError = Kernel32.INSTANCE.GetLastError();
        if (intByReference.getValue() == 0 || getLastError != 122) {
            throw new Win32Exception(getLastError);
        }
        final char[] array = new char[intByReference2.getValue()];
        final char[] array2 = new char[intByReference.getValue()];
        if (!Advapi32.INSTANCE.LookupAccountSid(null, psid, array2, intByReference, array, intByReference2, pointerByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final Account account = new Account();
        account.accountType = pointerByReference.getPointer().getInt(0L);
        account.name = Native.toString(array2);
        if (intByReference2.getValue() > 0) {
            account.domain = Native.toString(array);
            account.fqn = account.domain + "\\" + account.name;
        }
        else {
            account.fqn = account.name;
        }
        account.sid = psid.getBytes();
        account.sidString = convertSidToStringSid(psid);
        return account;
    }
    
    public static String convertSidToStringSid(final WinNT.PSID psid) {
        final PointerByReference pointerByReference = new PointerByReference();
        if (!Advapi32.INSTANCE.ConvertSidToStringSid(psid, pointerByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final String string = pointerByReference.getValue().getString(0L, true);
        Kernel32.INSTANCE.LocalFree(pointerByReference.getValue());
        return string;
    }
    
    public static byte[] convertStringSidToSid(final String s) {
        final WinNT.PSIDByReference psidByReference = new WinNT.PSIDByReference();
        if (!Advapi32.INSTANCE.ConvertStringSidToSid(s, psidByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return psidByReference.getValue().getBytes();
    }
    
    public static boolean isWellKnownSid(final String s, final int n) {
        final WinNT.PSIDByReference psidByReference = new WinNT.PSIDByReference();
        if (!Advapi32.INSTANCE.ConvertStringSidToSid(s, psidByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return Advapi32.INSTANCE.IsWellKnownSid(psidByReference.getValue(), n);
    }
    
    public static boolean isWellKnownSid(final byte[] array, final int n) {
        return Advapi32.INSTANCE.IsWellKnownSid(new WinNT.PSID(array), n);
    }
    
    public static Account getAccountBySid(final String s) {
        return getAccountBySid(null, s);
    }
    
    public static Account getAccountBySid(final String s, final String s2) {
        return getAccountBySid(s, new WinNT.PSID(convertStringSidToSid(s2)));
    }
    
    public static Account[] getTokenGroups(final WinNT.HANDLE handle) {
        final IntByReference intByReference = new IntByReference();
        if (Advapi32.INSTANCE.GetTokenInformation(handle, 2, null, 0, intByReference)) {
            throw new RuntimeException("Expected GetTokenInformation to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        final int getLastError = Kernel32.INSTANCE.GetLastError();
        if (getLastError != 122) {
            throw new Win32Exception(getLastError);
        }
        final WinNT.TOKEN_GROUPS token_GROUPS = new WinNT.TOKEN_GROUPS(intByReference.getValue());
        if (!Advapi32.INSTANCE.GetTokenInformation(handle, 2, token_GROUPS, intByReference.getValue(), intByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        final ArrayList<Account> list = new ArrayList<Account>();
        final WinNT.SID_AND_ATTRIBUTES[] groups = token_GROUPS.getGroups();
        while (0 < groups.length) {
            list.add(getAccountBySid(groups[0].Sid));
            int n = 0;
            ++n;
        }
        return list.toArray(new Account[0]);
    }
    
    public static Account getTokenAccount(final WinNT.HANDLE handle) {
        final IntByReference intByReference = new IntByReference();
        if (Advapi32.INSTANCE.GetTokenInformation(handle, 1, null, 0, intByReference)) {
            throw new RuntimeException("Expected GetTokenInformation to fail with ERROR_INSUFFICIENT_BUFFER");
        }
        final int getLastError = Kernel32.INSTANCE.GetLastError();
        if (getLastError != 122) {
            throw new Win32Exception(getLastError);
        }
        final WinNT.TOKEN_USER token_USER = new WinNT.TOKEN_USER(intByReference.getValue());
        if (!Advapi32.INSTANCE.GetTokenInformation(handle, 1, token_USER, intByReference.getValue(), intByReference)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return getAccountBySid(token_USER.User.Sid);
    }
    
    public static Account[] getCurrentUserGroups() {
        final WinNT.HANDLEByReference handleByReference = new WinNT.HANDLEByReference();
        if (!Advapi32.INSTANCE.OpenThreadToken(Kernel32.INSTANCE.GetCurrentThread(), 10, true, handleByReference)) {
            if (1008 != Kernel32.INSTANCE.GetLastError()) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
            if (!Advapi32.INSTANCE.OpenProcessToken(Kernel32.INSTANCE.GetCurrentProcess(), 10, handleByReference)) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
        }
        final Account[] tokenGroups = getTokenGroups(handleByReference.getValue());
        if (handleByReference.getValue() != WinBase.INVALID_HANDLE_VALUE && !Kernel32.INSTANCE.CloseHandle(handleByReference.getValue())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return tokenGroups;
    }
    
    public static boolean registryKeyExists(final WinReg.HKEY hkey, final String s) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        switch (regOpenKeyEx) {
            case 0: {
                Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
                return true;
            }
            case 2: {
                return false;
            }
            default: {
                throw new Win32Exception(regOpenKeyEx);
            }
        }
    }
    
    public static boolean registryValueExists(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        switch (regOpenKeyEx) {
            case 0: {
                final int regQueryValueEx = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, new IntByReference(), (char[])null, new IntByReference());
                switch (regQueryValueEx) {
                    case 0:
                    case 122: {
                        if (hkeyByReference.getValue() != WinBase.INVALID_HANDLE_VALUE) {
                            final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
                            if (regCloseKey != 0) {
                                throw new Win32Exception(regCloseKey);
                            }
                        }
                        return false;
                    }
                    case 2: {
                        if (hkeyByReference.getValue() != WinBase.INVALID_HANDLE_VALUE) {
                            final int regCloseKey2 = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
                            if (regCloseKey2 != 0) {
                                throw new Win32Exception(regCloseKey2);
                            }
                        }
                        return false;
                    }
                    default: {
                        throw new Win32Exception(regQueryValueEx);
                    }
                }
                break;
            }
            case 2: {
                if (hkeyByReference.getValue() != WinBase.INVALID_HANDLE_VALUE) {
                    final int regCloseKey3 = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
                    if (regCloseKey3 != 0) {
                        throw new Win32Exception(regCloseKey3);
                    }
                }
                return false;
            }
            default: {
                throw new Win32Exception(regOpenKeyEx);
            }
        }
    }
    
    public static String registryGetStringValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final int regQueryValueEx = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, (char[])null, intByReference);
        if (regQueryValueEx != 0 && regQueryValueEx != 122) {
            throw new Win32Exception(regQueryValueEx);
        }
        if (intByReference2.getValue() != 1 && intByReference2.getValue() != 2) {
            throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_SZ or REG_EXPAND_SZ");
        }
        final char[] array = new char[intByReference.getValue()];
        final int regQueryValueEx2 = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, array, intByReference);
        if (regQueryValueEx2 != 0 && regQueryValueEx2 != 122) {
            throw new Win32Exception(regQueryValueEx2);
        }
        final String string = Native.toString(array);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return string;
    }
    
    public static String registryGetExpandableStringValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final int regQueryValueEx = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, (char[])null, intByReference);
        if (regQueryValueEx != 0 && regQueryValueEx != 122) {
            throw new Win32Exception(regQueryValueEx);
        }
        if (intByReference2.getValue() != 2) {
            throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_SZ");
        }
        final char[] array = new char[intByReference.getValue()];
        final int regQueryValueEx2 = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, array, intByReference);
        if (regQueryValueEx2 != 0 && regQueryValueEx2 != 122) {
            throw new Win32Exception(regQueryValueEx2);
        }
        final String string = Native.toString(array);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return string;
    }
    
    public static String[] registryGetStringArray(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final int regQueryValueEx = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, (char[])null, intByReference);
        if (regQueryValueEx != 0 && regQueryValueEx != 122) {
            throw new Win32Exception(regQueryValueEx);
        }
        if (intByReference2.getValue() != 7) {
            throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_SZ");
        }
        final Memory memory = new Memory(intByReference.getValue());
        final int regQueryValueEx2 = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, memory, intByReference);
        if (regQueryValueEx2 != 0 && regQueryValueEx2 != 122) {
            throw new Win32Exception(regQueryValueEx2);
        }
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < memory.size()) {
            final String string = memory.getString(0, true);
            final int n = 0 + string.length() * Native.WCHAR_SIZE;
            final int n2 = 0 + Native.WCHAR_SIZE;
            list.add(string);
        }
        final String[] array = list.toArray(new String[0]);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return array;
    }
    
    public static byte[] registryGetBinaryValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final int regQueryValueEx = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, (char[])null, intByReference);
        if (regQueryValueEx != 0 && regQueryValueEx != 122) {
            throw new Win32Exception(regQueryValueEx);
        }
        if (intByReference2.getValue() != 3) {
            throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_BINARY");
        }
        final byte[] array = new byte[intByReference.getValue()];
        final int regQueryValueEx2 = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, array, intByReference);
        if (regQueryValueEx2 != 0 && regQueryValueEx2 != 122) {
            throw new Win32Exception(regQueryValueEx2);
        }
        final byte[] array2 = array;
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return array2;
    }
    
    public static int registryGetIntValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final int regQueryValueEx = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, (char[])null, intByReference);
        if (regQueryValueEx != 0 && regQueryValueEx != 122) {
            throw new Win32Exception(regQueryValueEx);
        }
        if (intByReference2.getValue() != 4) {
            throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_DWORD");
        }
        final IntByReference intByReference3 = new IntByReference();
        final int regQueryValueEx2 = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, intByReference3, intByReference);
        if (regQueryValueEx2 != 0 && regQueryValueEx2 != 122) {
            throw new Win32Exception(regQueryValueEx2);
        }
        final int value = intByReference3.getValue();
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return value;
    }
    
    public static long registryGetLongValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final int regQueryValueEx = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, (char[])null, intByReference);
        if (regQueryValueEx != 0 && regQueryValueEx != 122) {
            throw new Win32Exception(regQueryValueEx);
        }
        if (intByReference2.getValue() != 11) {
            throw new RuntimeException("Unexpected registry type " + intByReference2.getValue() + ", expected REG_QWORD");
        }
        final LongByReference longByReference = new LongByReference();
        final int regQueryValueEx2 = Advapi32.INSTANCE.RegQueryValueEx(hkeyByReference.getValue(), s2, 0, intByReference2, longByReference, intByReference);
        if (regQueryValueEx2 != 0 && regQueryValueEx2 != 122) {
            throw new Win32Exception(regQueryValueEx2);
        }
        final long value = longByReference.getValue();
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return value;
    }
    
    public static boolean registryCreateKey(final WinReg.HKEY hkey, final String s) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final IntByReference intByReference = new IntByReference();
        final int regCreateKeyEx = Advapi32.INSTANCE.RegCreateKeyEx(hkey, s, 0, null, 0, 131097, null, hkeyByReference, intByReference);
        if (regCreateKeyEx != 0) {
            throw new Win32Exception(regCreateKeyEx);
        }
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return 1 == intByReference.getValue();
    }
    
    public static boolean registryCreateKey(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 4, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final boolean registryCreateKey = registryCreateKey(hkeyByReference.getValue(), s2);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return registryCreateKey;
    }
    
    public static void registrySetIntValue(final WinReg.HKEY hkey, final String s, final int n) {
        final int regSetValueEx = Advapi32.INSTANCE.RegSetValueEx(hkey, s, 0, 4, new byte[] { (byte)(n & 0xFF), (byte)(n >> 8 & 0xFF), (byte)(n >> 16 & 0xFF), (byte)(n >> 24 & 0xFF) }, 4);
        if (regSetValueEx != 0) {
            throw new Win32Exception(regSetValueEx);
        }
    }
    
    public static void registrySetIntValue(final WinReg.HKEY hkey, final String s, final String s2, final int n) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registrySetIntValue(hkeyByReference.getValue(), s2, n);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static void registrySetLongValue(final WinReg.HKEY hkey, final String s, final long n) {
        final int regSetValueEx = Advapi32.INSTANCE.RegSetValueEx(hkey, s, 0, 11, new byte[] { (byte)(n & 0xFFL), (byte)(n >> 8 & 0xFFL), (byte)(n >> 16 & 0xFFL), (byte)(n >> 24 & 0xFFL), (byte)(n >> 32 & 0xFFL), (byte)(n >> 40 & 0xFFL), (byte)(n >> 48 & 0xFFL), (byte)(n >> 56 & 0xFFL) }, 8);
        if (regSetValueEx != 0) {
            throw new Win32Exception(regSetValueEx);
        }
    }
    
    public static void registrySetLongValue(final WinReg.HKEY hkey, final String s, final String s2, final long n) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registrySetLongValue(hkeyByReference.getValue(), s2, n);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static void registrySetStringValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final char[] charArray = Native.toCharArray(s2);
        final int regSetValueEx = Advapi32.INSTANCE.RegSetValueEx(hkey, s, 0, 1, charArray, charArray.length * Native.WCHAR_SIZE);
        if (regSetValueEx != 0) {
            throw new Win32Exception(regSetValueEx);
        }
    }
    
    public static void registrySetStringValue(final WinReg.HKEY hkey, final String s, final String s2, final String s3) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registrySetStringValue(hkeyByReference.getValue(), s2, s3);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static void registrySetExpandableStringValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final char[] charArray = Native.toCharArray(s2);
        final int regSetValueEx = Advapi32.INSTANCE.RegSetValueEx(hkey, s, 0, 2, charArray, charArray.length * Native.WCHAR_SIZE);
        if (regSetValueEx != 0) {
            throw new Win32Exception(regSetValueEx);
        }
    }
    
    public static void registrySetExpandableStringValue(final WinReg.HKEY hkey, final String s, final String s2, final String s3) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registrySetExpandableStringValue(hkeyByReference.getValue(), s2, s3);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static void registrySetStringArray(final WinReg.HKEY hkey, final String s, final String[] array) {
        while (0 < array.length) {
            final int n = 0 + array[0].length() * Native.WCHAR_SIZE;
            final int n2 = 0 + Native.WCHAR_SIZE;
            int regSetValueEx = 0;
            ++regSetValueEx;
        }
        final Memory memory = new Memory(0);
        while (0 < array.length) {
            final String s2 = array[0];
            memory.setString(0, s2, true);
            final int n3 = 0 + s2.length() * Native.WCHAR_SIZE;
            final int n4 = 0 + Native.WCHAR_SIZE;
            int n5 = 0;
            ++n5;
        }
        int regSetValueEx = Advapi32.INSTANCE.RegSetValueEx(hkey, s, 0, 7, memory.getByteArray(0L, 0), 0);
        if (false) {
            throw new Win32Exception(0);
        }
    }
    
    public static void registrySetStringArray(final WinReg.HKEY hkey, final String s, final String s2, final String[] array) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registrySetStringArray(hkeyByReference.getValue(), s2, array);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static void registrySetBinaryValue(final WinReg.HKEY hkey, final String s, final byte[] array) {
        final int regSetValueEx = Advapi32.INSTANCE.RegSetValueEx(hkey, s, 0, 3, array, array.length);
        if (regSetValueEx != 0) {
            throw new Win32Exception(regSetValueEx);
        }
    }
    
    public static void registrySetBinaryValue(final WinReg.HKEY hkey, final String s, final String s2, final byte[] array) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registrySetBinaryValue(hkeyByReference.getValue(), s2, array);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static void registryDeleteKey(final WinReg.HKEY hkey, final String s) {
        final int regDeleteKey = Advapi32.INSTANCE.RegDeleteKey(hkey, s);
        if (regDeleteKey != 0) {
            throw new Win32Exception(regDeleteKey);
        }
    }
    
    public static void registryDeleteKey(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registryDeleteKey(hkeyByReference.getValue(), s2);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static void registryDeleteValue(final WinReg.HKEY hkey, final String s) {
        final int regDeleteValue = Advapi32.INSTANCE.RegDeleteValue(hkey, s);
        if (regDeleteValue != 0) {
            throw new Win32Exception(regDeleteValue);
        }
    }
    
    public static void registryDeleteValue(final WinReg.HKEY hkey, final String s, final String s2) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131103, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        registryDeleteValue(hkeyByReference.getValue(), s2);
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
    }
    
    public static String[] registryGetKeys(final WinReg.HKEY hkey) {
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final int regQueryInfoKey = Advapi32.INSTANCE.RegQueryInfoKey(hkey, null, null, null, intByReference, intByReference2, null, null, null, null, null, null);
        if (regQueryInfoKey != 0) {
            throw new Win32Exception(regQueryInfoKey);
        }
        final ArrayList list = new ArrayList<String>(intByReference.getValue());
        final char[] array = new char[intByReference2.getValue() + 1];
        while (0 < intByReference.getValue()) {
            final int regEnumKeyEx = Advapi32.INSTANCE.RegEnumKeyEx(hkey, 0, array, new IntByReference(intByReference2.getValue() + 1), null, null, null, null);
            if (regEnumKeyEx != 0) {
                throw new Win32Exception(regEnumKeyEx);
            }
            list.add(Native.toString(array));
            int n = 0;
            ++n;
        }
        return list.toArray(new String[0]);
    }
    
    public static String[] registryGetKeys(final WinReg.HKEY hkey, final String s) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final String[] registryGetKeys = registryGetKeys(hkeyByReference.getValue());
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return registryGetKeys;
    }
    
    public static TreeMap registryGetValues(final WinReg.HKEY hkey) {
        final IntByReference intByReference = new IntByReference();
        final IntByReference intByReference2 = new IntByReference();
        final IntByReference intByReference3 = new IntByReference();
        final int regQueryInfoKey = Advapi32.INSTANCE.RegQueryInfoKey(hkey, null, null, null, null, null, null, intByReference, intByReference2, intByReference3, null, null);
        if (regQueryInfoKey != 0) {
            throw new Win32Exception(regQueryInfoKey);
        }
        final TreeMap<String, Long> treeMap = new TreeMap<String, Long>();
        final char[] array = new char[intByReference2.getValue() + 1];
        final byte[] array2 = new byte[intByReference3.getValue()];
        while (0 < intByReference.getValue()) {
            final IntByReference intByReference4 = new IntByReference(intByReference2.getValue() + 1);
            final IntByReference intByReference5 = new IntByReference(intByReference3.getValue());
            final IntByReference intByReference6 = new IntByReference();
            final int regEnumValue = Advapi32.INSTANCE.RegEnumValue(hkey, 0, array, intByReference4, null, intByReference6, array2, intByReference5);
            if (regEnumValue != 0) {
                throw new Win32Exception(regEnumValue);
            }
            final String string = Native.toString(array);
            final Memory memory = new Memory(intByReference5.getValue());
            memory.write(0L, array2, 0, intByReference5.getValue());
            switch (intByReference6.getValue()) {
                case 11: {
                    treeMap.put(string, memory.getLong(0L));
                    break;
                }
                case 4: {
                    treeMap.put(string, Long.valueOf(Integer.valueOf(memory.getInt(0L))));
                    break;
                }
                case 1:
                case 2: {
                    treeMap.put(string, (Long)memory.getString(0L, true));
                    break;
                }
                case 3: {
                    treeMap.put(string, (Long)(Object)memory.getByteArray(0L, intByReference5.getValue()));
                    break;
                }
                case 7: {
                    final Memory memory2 = new Memory(intByReference5.getValue());
                    memory2.write(0L, array2, 0, intByReference5.getValue());
                    final ArrayList<String> list = new ArrayList<String>();
                    while (0 < memory2.size()) {
                        final String string2 = memory2.getString(0, true);
                        final int n = 0 + string2.length() * Native.WCHAR_SIZE;
                        final int n2 = 0 + Native.WCHAR_SIZE;
                        list.add(string2);
                    }
                    treeMap.put(string, (Long)(Object)list.toArray(new String[0]));
                    break;
                }
                default: {
                    throw new RuntimeException("Unsupported type: " + intByReference6.getValue());
                }
            }
            int n3 = 0;
            ++n3;
        }
        return treeMap;
    }
    
    public static TreeMap registryGetValues(final WinReg.HKEY hkey, final String s) {
        final WinReg.HKEYByReference hkeyByReference = new WinReg.HKEYByReference();
        final int regOpenKeyEx = Advapi32.INSTANCE.RegOpenKeyEx(hkey, s, 0, 131097, hkeyByReference);
        if (regOpenKeyEx != 0) {
            throw new Win32Exception(regOpenKeyEx);
        }
        final TreeMap registryGetValues = registryGetValues(hkeyByReference.getValue());
        final int regCloseKey = Advapi32.INSTANCE.RegCloseKey(hkeyByReference.getValue());
        if (regCloseKey != 0) {
            throw new Win32Exception(regCloseKey);
        }
        return registryGetValues;
    }
    
    public static String getEnvironmentBlock(final Map map) {
        final StringBuffer sb = new StringBuffer();
        for (final Map.Entry<String, V> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(entry.getKey() + "=" + (String)entry.getValue() + "\u0000");
            }
        }
        return sb.toString() + "\u0000";
    }
    
    public static WinNT.ACCESS_ACEStructure[] getFileSecurity(final String s, final boolean b) {
        Memory memory;
        do {
            memory = new Memory(1024);
            final IntByReference intByReference = new IntByReference();
            if (!Advapi32.INSTANCE.GetFileSecurity(new WString(s), 4, memory, 1024, intByReference)) {
                final int getLastError = Kernel32.INSTANCE.GetLastError();
                memory.clear();
                if (122 != getLastError) {
                    throw new Win32Exception(getLastError);
                }
            }
            if (1024 < intByReference.getValue()) {
                memory.clear();
            }
        } while (true);
        final WinNT.SECURITY_DESCRIPTOR_RELATIVE security_DESCRIPTOR_RELATIVE = new WinNT.SECURITY_DESCRIPTOR_RELATIVE(memory);
        memory.clear();
        final WinNT.ACCESS_ACEStructure[] aceStructures = security_DESCRIPTOR_RELATIVE.getDiscretionaryACL().getACEStructures();
        if (b) {
            final HashMap<String, WinNT.ACCESS_ACEStructure> hashMap = (HashMap<String, WinNT.ACCESS_ACEStructure>)new HashMap<Object, Object>();
            final WinNT.ACCESS_ACEStructure[] array = aceStructures;
            while (0 < array.length) {
                final WinNT.ACCESS_ACEStructure access_ACEStructure = array[0];
                final String string = access_ACEStructure.getSidString() + "/" + ((access_ACEStructure.AceFlags & 0x1F) != 0x0) + "/" + access_ACEStructure.getClass().getName();
                final WinNT.ACCESS_ACEStructure access_ACEStructure2 = hashMap.get(string);
                if (access_ACEStructure2 != null) {
                    access_ACEStructure2.Mask |= access_ACEStructure.Mask;
                }
                else {
                    hashMap.put(string, access_ACEStructure);
                }
                int n = 0;
                ++n;
            }
            return hashMap.values().toArray(new WinNT.ACCESS_ACEStructure[hashMap.size()]);
        }
        return aceStructures;
    }
    
    public static class EventLogIterator implements Iterable, Iterator
    {
        private WinNT.HANDLE _h;
        private Memory _buffer;
        private boolean _done;
        private int _dwRead;
        private Pointer _pevlr;
        private int _flags;
        
        public EventLogIterator(final String s) {
            this(null, s, 4);
        }
        
        public EventLogIterator(final String s, final String s2, final int flags) {
            this._h = null;
            this._buffer = new Memory(65536L);
            this._done = false;
            this._dwRead = 0;
            this._pevlr = null;
            this._flags = 4;
            this._flags = flags;
            this._h = Advapi32.INSTANCE.OpenEventLog(s, s2);
            if (this._h == null) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }
        }
        
        private boolean read() {
            if (this._done || this._dwRead > 0) {
                return false;
            }
            final IntByReference intByReference = new IntByReference();
            final IntByReference intByReference2 = new IntByReference();
            if (!Advapi32.INSTANCE.ReadEventLog(this._h, 0x1 | this._flags, 0, this._buffer, (int)this._buffer.size(), intByReference, intByReference2)) {
                final int getLastError = Kernel32.INSTANCE.GetLastError();
                if (getLastError == 122) {
                    this._buffer = new Memory(intByReference2.getValue());
                    if (!Advapi32.INSTANCE.ReadEventLog(this._h, 0x1 | this._flags, 0, this._buffer, (int)this._buffer.size(), intByReference, intByReference2)) {
                        throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                    }
                }
                else {
                    this.close();
                    if (getLastError != 38) {
                        throw new Win32Exception(getLastError);
                    }
                    return false;
                }
            }
            this._dwRead = intByReference.getValue();
            this._pevlr = this._buffer;
            return true;
        }
        
        public void close() {
            this._done = true;
            if (this._h != null) {
                if (!Advapi32.INSTANCE.CloseEventLog(this._h)) {
                    throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
                }
                this._h = null;
            }
        }
        
        public Iterator iterator() {
            return this;
        }
        
        public boolean hasNext() {
            this.read();
            return !this._done;
        }
        
        public EventLogRecord next() {
            this.read();
            final EventLogRecord eventLogRecord = new EventLogRecord(this._pevlr);
            this._dwRead -= eventLogRecord.getLength();
            this._pevlr = this._pevlr.share(eventLogRecord.getLength());
            return eventLogRecord;
        }
        
        public void remove() {
        }
        
        public Object next() {
            return this.next();
        }
    }
    
    public static class EventLogRecord
    {
        private WinNT.EVENTLOGRECORD _record;
        private String _source;
        private byte[] _data;
        private String[] _strings;
        
        public WinNT.EVENTLOGRECORD getRecord() {
            return this._record;
        }
        
        public int getEventId() {
            return this._record.EventID.intValue();
        }
        
        public String getSource() {
            return this._source;
        }
        
        public int getStatusCode() {
            return this._record.EventID.intValue() & 0xFFFF;
        }
        
        public int getRecordNumber() {
            return this._record.RecordNumber.intValue();
        }
        
        public int getLength() {
            return this._record.Length.intValue();
        }
        
        public String[] getStrings() {
            return this._strings;
        }
        
        public EventLogType getType() {
            switch (this._record.EventType.intValue()) {
                case 0:
                case 4: {
                    return EventLogType.Informational;
                }
                case 16: {
                    return EventLogType.AuditFailure;
                }
                case 8: {
                    return EventLogType.AuditSuccess;
                }
                case 1: {
                    return EventLogType.Error;
                }
                case 2: {
                    return EventLogType.Warning;
                }
                default: {
                    throw new RuntimeException("Invalid type: " + this._record.EventType.intValue());
                }
            }
        }
        
        public byte[] getData() {
            return this._data;
        }
        
        public EventLogRecord(final Pointer pointer) {
            this._record = null;
            this._record = new WinNT.EVENTLOGRECORD(pointer);
            this._source = pointer.getString(this._record.size(), true);
            if (this._record.DataLength.intValue() > 0) {
                this._data = pointer.getByteArray(this._record.DataOffset.intValue(), this._record.DataLength.intValue());
            }
            if (this._record.NumStrings.intValue() > 0) {
                final ArrayList<String> list = new ArrayList<String>();
                int i = this._record.NumStrings.intValue();
                long n = this._record.StringOffset.intValue();
                while (i > 0) {
                    final String string = pointer.getString(n, true);
                    list.add(string);
                    n = n + string.length() * Native.WCHAR_SIZE + Native.WCHAR_SIZE;
                    --i;
                }
                this._strings = list.toArray(new String[0]);
            }
        }
    }
    
    public enum EventLogType
    {
        Error("Error", 0), 
        Warning("Warning", 1), 
        Informational("Informational", 2), 
        AuditSuccess("AuditSuccess", 3), 
        AuditFailure("AuditFailure", 4);
        
        private static final EventLogType[] $VALUES;
        
        private EventLogType(final String s, final int n) {
        }
        
        static {
            $VALUES = new EventLogType[] { EventLogType.Error, EventLogType.Warning, EventLogType.Informational, EventLogType.AuditSuccess, EventLogType.AuditFailure };
        }
    }
    
    public static class Account
    {
        public String name;
        public String domain;
        public byte[] sid;
        public String sidString;
        public int accountType;
        public String fqn;
    }
}
