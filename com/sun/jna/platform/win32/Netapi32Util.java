package com.sun.jna.platform.win32;

import java.util.*;
import com.sun.jna.ptr.*;
import com.sun.jna.*;

public abstract class Netapi32Util
{
    public static String getDCName() {
        return getDCName(null, null);
    }
    
    public static String getDCName(final String s, final String s2) {
        final PointerByReference pointerByReference = new PointerByReference();
        final int netGetDCName = Netapi32.INSTANCE.NetGetDCName(s2, s, pointerByReference);
        if (netGetDCName != 0) {
            throw new Win32Exception(netGetDCName);
        }
        final String string = pointerByReference.getValue().getString(0L, true);
        if (0 != Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue())) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }
        return string;
    }
    
    public static int getJoinStatus() {
        return getJoinStatus(null);
    }
    
    public static int getJoinStatus(final String s) {
        final PointerByReference pointerByReference = new PointerByReference();
        final IntByReference intByReference = new IntByReference();
        final int netGetJoinInformation = Netapi32.INSTANCE.NetGetJoinInformation(s, pointerByReference, intByReference);
        if (netGetJoinInformation != 0) {
            throw new Win32Exception(netGetJoinInformation);
        }
        final int value = intByReference.getValue();
        if (pointerByReference.getPointer() != null) {
            final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            if (netApiBufferFree != 0) {
                throw new Win32Exception(netApiBufferFree);
            }
        }
        return value;
    }
    
    public static String getDomainName(final String s) {
        final PointerByReference pointerByReference = new PointerByReference();
        final int netGetJoinInformation = Netapi32.INSTANCE.NetGetJoinInformation(s, pointerByReference, new IntByReference());
        if (netGetJoinInformation != 0) {
            throw new Win32Exception(netGetJoinInformation);
        }
        final String string = pointerByReference.getValue().getString(0L, true);
        if (pointerByReference.getPointer() != null) {
            final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            if (netApiBufferFree != 0) {
                throw new Win32Exception(netApiBufferFree);
            }
        }
        return string;
    }
    
    public static LocalGroup[] getLocalGroups() {
        return getLocalGroups(null);
    }
    
    public static LocalGroup[] getLocalGroups(final String s) {
        final PointerByReference pointerByReference = new PointerByReference();
        final IntByReference intByReference = new IntByReference();
        final int netLocalGroupEnum = Netapi32.INSTANCE.NetLocalGroupEnum(s, 1, pointerByReference, -1, intByReference, new IntByReference(), null);
        if (netLocalGroupEnum || pointerByReference.getValue() == Pointer.NULL) {
            throw new Win32Exception(netLocalGroupEnum);
        }
        final LMAccess.LOCALGROUP_INFO_1[] array = (LMAccess.LOCALGROUP_INFO_1[])new LMAccess.LOCALGROUP_INFO_1(pointerByReference.getValue()).toArray(intByReference.getValue());
        final ArrayList<LocalGroup> list = new ArrayList<LocalGroup>();
        final LMAccess.LOCALGROUP_INFO_1[] array2 = array;
        while (0 < array2.length) {
            final LMAccess.LOCALGROUP_INFO_1 localgroup_INFO_1 = array2[0];
            final LocalGroup localGroup = new LocalGroup();
            localGroup.name = localgroup_INFO_1.lgrui1_name.toString();
            localGroup.comment = localgroup_INFO_1.lgrui1_comment.toString();
            list.add(localGroup);
            int n = 0;
            ++n;
        }
        final LocalGroup[] array3 = list.toArray(new LocalGroup[0]);
        if (pointerByReference.getValue() != Pointer.NULL) {
            final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            if (netApiBufferFree != 0) {
                throw new Win32Exception(netApiBufferFree);
            }
        }
        return array3;
    }
    
    public static Group[] getGlobalGroups() {
        return getGlobalGroups(null);
    }
    
    public static Group[] getGlobalGroups(final String s) {
        final PointerByReference pointerByReference = new PointerByReference();
        final IntByReference intByReference = new IntByReference();
        final int netGroupEnum = Netapi32.INSTANCE.NetGroupEnum(s, 1, pointerByReference, -1, intByReference, new IntByReference(), null);
        if (netGroupEnum || pointerByReference.getValue() == Pointer.NULL) {
            throw new Win32Exception(netGroupEnum);
        }
        final LMAccess.GROUP_INFO_1[] array = (LMAccess.GROUP_INFO_1[])new LMAccess.GROUP_INFO_1(pointerByReference.getValue()).toArray(intByReference.getValue());
        final ArrayList<LocalGroup> list = new ArrayList<LocalGroup>();
        final LMAccess.GROUP_INFO_1[] array2 = array;
        while (0 < array2.length) {
            final LMAccess.GROUP_INFO_1 group_INFO_1 = array2[0];
            final LocalGroup localGroup = new LocalGroup();
            localGroup.name = group_INFO_1.grpi1_name.toString();
            localGroup.comment = group_INFO_1.grpi1_comment.toString();
            list.add(localGroup);
            int n = 0;
            ++n;
        }
        final LocalGroup[] array3 = list.toArray(new LocalGroup[0]);
        if (pointerByReference.getValue() != Pointer.NULL) {
            final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            if (netApiBufferFree != 0) {
                throw new Win32Exception(netApiBufferFree);
            }
        }
        return array3;
    }
    
    public static User[] getUsers() {
        return getUsers(null);
    }
    
    public static User[] getUsers(final String s) {
        final PointerByReference pointerByReference = new PointerByReference();
        final IntByReference intByReference = new IntByReference();
        final int netUserEnum = Netapi32.INSTANCE.NetUserEnum(s, 1, 0, pointerByReference, -1, intByReference, new IntByReference(), null);
        if (netUserEnum || pointerByReference.getValue() == Pointer.NULL) {
            throw new Win32Exception(netUserEnum);
        }
        final LMAccess.USER_INFO_1[] array = (LMAccess.USER_INFO_1[])new LMAccess.USER_INFO_1(pointerByReference.getValue()).toArray(intByReference.getValue());
        final ArrayList<User> list = new ArrayList<User>();
        final LMAccess.USER_INFO_1[] array2 = array;
        while (0 < array2.length) {
            final LMAccess.USER_INFO_1 user_INFO_1 = array2[0];
            final User user = new User();
            user.name = user_INFO_1.usri1_name.toString();
            list.add(user);
            int n = 0;
            ++n;
        }
        final User[] array3 = list.toArray(new User[0]);
        if (pointerByReference.getValue() != Pointer.NULL) {
            final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            if (netApiBufferFree != 0) {
                throw new Win32Exception(netApiBufferFree);
            }
        }
        return array3;
    }
    
    public static Group[] getCurrentUserLocalGroups() {
        return getUserLocalGroups(Secur32Util.getUserNameEx(2));
    }
    
    public static Group[] getUserLocalGroups(final String s) {
        return getUserLocalGroups(s, null);
    }
    
    public static Group[] getUserLocalGroups(final String s, final String s2) {
        final PointerByReference pointerByReference = new PointerByReference();
        final IntByReference intByReference = new IntByReference();
        final int netUserGetLocalGroups = Netapi32.INSTANCE.NetUserGetLocalGroups(s2, s, 0, 0, pointerByReference, -1, intByReference, new IntByReference());
        if (netUserGetLocalGroups != 0) {
            throw new Win32Exception(netUserGetLocalGroups);
        }
        final LMAccess.LOCALGROUP_USERS_INFO_0[] array = (LMAccess.LOCALGROUP_USERS_INFO_0[])new LMAccess.LOCALGROUP_USERS_INFO_0(pointerByReference.getValue()).toArray(intByReference.getValue());
        final ArrayList<LocalGroup> list = new ArrayList<LocalGroup>();
        final LMAccess.LOCALGROUP_USERS_INFO_0[] array2 = array;
        while (0 < array2.length) {
            final LMAccess.LOCALGROUP_USERS_INFO_0 localgroup_USERS_INFO_0 = array2[0];
            final LocalGroup localGroup = new LocalGroup();
            localGroup.name = localgroup_USERS_INFO_0.lgrui0_name.toString();
            list.add(localGroup);
            int n = 0;
            ++n;
        }
        final Group[] array3 = list.toArray(new Group[0]);
        if (pointerByReference.getValue() != Pointer.NULL) {
            final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            if (netApiBufferFree != 0) {
                throw new Win32Exception(netApiBufferFree);
            }
        }
        return array3;
    }
    
    public static Group[] getUserGroups(final String s) {
        return getUserGroups(s, null);
    }
    
    public static Group[] getUserGroups(final String s, final String s2) {
        final PointerByReference pointerByReference = new PointerByReference();
        final IntByReference intByReference = new IntByReference();
        final int netUserGetGroups = Netapi32.INSTANCE.NetUserGetGroups(s2, s, 0, pointerByReference, -1, intByReference, new IntByReference());
        if (netUserGetGroups != 0) {
            throw new Win32Exception(netUserGetGroups);
        }
        final LMAccess.GROUP_USERS_INFO_0[] array = (LMAccess.GROUP_USERS_INFO_0[])new LMAccess.GROUP_USERS_INFO_0(pointerByReference.getValue()).toArray(intByReference.getValue());
        final ArrayList<Group> list = new ArrayList<Group>();
        final LMAccess.GROUP_USERS_INFO_0[] array2 = array;
        while (0 < array2.length) {
            final LMAccess.GROUP_USERS_INFO_0 group_USERS_INFO_0 = array2[0];
            final Group group = new Group();
            group.name = group_USERS_INFO_0.grui0_name.toString();
            list.add(group);
            int n = 0;
            ++n;
        }
        final Group[] array3 = list.toArray(new Group[0]);
        if (pointerByReference.getValue() != Pointer.NULL) {
            final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            if (netApiBufferFree != 0) {
                throw new Win32Exception(netApiBufferFree);
            }
        }
        return array3;
    }
    
    public static DomainController getDC() {
        final DsGetDC.PDOMAIN_CONTROLLER_INFO.ByReference byReference = new DsGetDC.PDOMAIN_CONTROLLER_INFO.ByReference();
        final int dsGetDcName = Netapi32.INSTANCE.DsGetDcName(null, null, null, null, 0, byReference);
        if (dsGetDcName != 0) {
            throw new Win32Exception(dsGetDcName);
        }
        final DomainController domainController = new DomainController();
        domainController.address = byReference.dci.DomainControllerAddress.toString();
        domainController.addressType = byReference.dci.DomainControllerAddressType;
        domainController.clientSiteName = byReference.dci.ClientSiteName.toString();
        domainController.dnsForestName = byReference.dci.DnsForestName.toString();
        domainController.domainGuid = byReference.dci.DomainGuid;
        domainController.domainName = byReference.dci.DomainName.toString();
        domainController.flags = byReference.dci.Flags;
        domainController.name = byReference.dci.DomainControllerName.toString();
        final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(byReference.dci.getPointer());
        if (netApiBufferFree != 0) {
            throw new Win32Exception(netApiBufferFree);
        }
        return domainController;
    }
    
    public static DomainTrust[] getDomainTrusts() {
        return getDomainTrusts(null);
    }
    
    public static DomainTrust[] getDomainTrusts(final String s) {
        final NativeLongByReference nativeLongByReference = new NativeLongByReference();
        final DsGetDC.PDS_DOMAIN_TRUSTS.ByReference byReference = new DsGetDC.PDS_DOMAIN_TRUSTS.ByReference();
        final int dsEnumerateDomainTrusts = Netapi32.INSTANCE.DsEnumerateDomainTrusts(s, new NativeLong(63L), byReference, nativeLongByReference);
        if (dsEnumerateDomainTrusts != 0) {
            throw new Win32Exception(dsEnumerateDomainTrusts);
        }
        final int intValue = nativeLongByReference.getValue().intValue();
        final ArrayList list = new ArrayList<DomainTrust>(intValue);
        final DsGetDC.DS_DOMAIN_TRUSTS[] trusts = byReference.getTrusts(intValue);
        while (0 < trusts.length) {
            final DsGetDC.DS_DOMAIN_TRUSTS ds_DOMAIN_TRUSTS = trusts[0];
            final DomainTrust domainTrust = new DomainTrust();
            domainTrust.DnsDomainName = ds_DOMAIN_TRUSTS.DnsDomainName.toString();
            domainTrust.NetbiosDomainName = ds_DOMAIN_TRUSTS.NetbiosDomainName.toString();
            domainTrust.DomainSid = ds_DOMAIN_TRUSTS.DomainSid;
            domainTrust.DomainSidString = Advapi32Util.convertSidToStringSid(ds_DOMAIN_TRUSTS.DomainSid);
            domainTrust.DomainGuid = ds_DOMAIN_TRUSTS.DomainGuid;
            domainTrust.DomainGuidString = Ole32Util.getStringFromGUID(ds_DOMAIN_TRUSTS.DomainGuid);
            DomainTrust.access$002(domainTrust, ds_DOMAIN_TRUSTS.Flags.intValue());
            list.add(domainTrust);
            int n = 0;
            ++n;
        }
        final DomainTrust[] array = list.toArray(new DomainTrust[0]);
        final int netApiBufferFree = Netapi32.INSTANCE.NetApiBufferFree(byReference.getPointer());
        if (netApiBufferFree != 0) {
            throw new Win32Exception(netApiBufferFree);
        }
        return array;
    }
    
    public static UserInfo getUserInfo(final String s) {
        return getUserInfo(s, getDCName());
    }
    
    public static UserInfo getUserInfo(final String s, final String s2) {
        final PointerByReference pointerByReference = new PointerByReference();
        Netapi32.INSTANCE.NetUserGetInfo(getDCName(), s, 23, pointerByReference);
        if (-1 == 0) {
            final LMAccess.USER_INFO_23 user_INFO_23 = new LMAccess.USER_INFO_23(pointerByReference.getValue());
            final UserInfo userInfo = new UserInfo();
            userInfo.comment = user_INFO_23.usri23_comment.toString();
            userInfo.flags = user_INFO_23.usri23_flags;
            userInfo.fullName = user_INFO_23.usri23_full_name.toString();
            userInfo.name = user_INFO_23.usri23_name.toString();
            userInfo.sidString = Advapi32Util.convertSidToStringSid(user_INFO_23.usri23_user_sid);
            userInfo.sid = user_INFO_23.usri23_user_sid;
            final UserInfo userInfo2 = userInfo;
            if (pointerByReference.getValue() != Pointer.NULL) {
                Netapi32.INSTANCE.NetApiBufferFree(pointerByReference.getValue());
            }
            return userInfo2;
        }
        throw new Win32Exception(-1);
    }
    
    public static class DomainTrust
    {
        public String NetbiosDomainName;
        public String DnsDomainName;
        public WinNT.PSID DomainSid;
        public String DomainSidString;
        public Guid.GUID DomainGuid;
        public String DomainGuidString;
        private int flags;
        
        public boolean isInForest() {
            return (this.flags & 0x1) != 0x0;
        }
        
        public boolean isOutbound() {
            return (this.flags & 0x2) != 0x0;
        }
        
        public boolean isRoot() {
            return (this.flags & 0x4) != 0x0;
        }
        
        public boolean isPrimary() {
            return (this.flags & 0x8) != 0x0;
        }
        
        public boolean isNativeMode() {
            return (this.flags & 0x10) != 0x0;
        }
        
        public boolean isInbound() {
            return (this.flags & 0x20) != 0x0;
        }
        
        static int access$002(final DomainTrust domainTrust, final int flags) {
            return domainTrust.flags = flags;
        }
    }
    
    public static class DomainController
    {
        public String name;
        public String address;
        public int addressType;
        public Guid.GUID domainGuid;
        public String domainName;
        public String dnsForestName;
        public int flags;
        public String clientSiteName;
    }
    
    public static class LocalGroup extends Group
    {
        public String comment;
    }
    
    public static class Group
    {
        public String name;
    }
    
    public static class UserInfo extends User
    {
        public String fullName;
        public String sidString;
        public WinNT.PSID sid;
        public int flags;
    }
    
    public static class User
    {
        public String name;
        public String comment;
    }
}
