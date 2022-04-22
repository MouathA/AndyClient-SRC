package com.mojang.realmsclient.client;

import com.google.gson.*;
import java.io.*;
import com.mojang.realmsclient.dto.*;
import java.net.*;
import net.minecraft.realms.*;
import com.mojang.realmsclient.*;
import com.mojang.realmsclient.exception.*;
import org.apache.logging.log4j.*;

public class RealmsClient
{
    private static final Logger LOGGER;
    private final String sessionId;
    private final String username;
    private static String baseUrl;
    private static final String WORLDS_RESOURCE_PATH = "worlds";
    private static final String INVITES_RESOURCE_PATH = "invites";
    private static final String MCO_RESOURCE_PATH = "mco";
    private static final String SUBSCRIPTION_RESOURCE = "subscriptions";
    private static final String ACTIVITIES_RESOURCE = "activities";
    private static final String OPS_RESOURCE = "ops";
    private static final String REGIONS_RESOURCE = "regions/ping/stat";
    private static final String TRIALS_RESOURCE = "trial";
    private static final String PATH_INITIALIZE = "/$WORLD_ID/initialize";
    private static final String PATH_GET_ACTIVTIES = "/$WORLD_ID";
    private static final String PATH_GET_SUBSCRIPTION = "/$WORLD_ID";
    private static final String PATH_GET_MINIGAMES = "/minigames";
    private static final String PATH_OP = "/$WORLD_ID";
    private static final String PATH_PUT_INTO_MINIGAMES_MODE = "/minigames/$MINIGAME_ID/$WORLD_ID";
    private static final String PATH_AVAILABLE = "/available";
    private static final String PATH_TEMPLATES = "/templates";
    private static final String PATH_WORLD_JOIN = "/$ID/join";
    private static final String PATH_WORLD_GET = "/$ID";
    private static final String PATH_WORLD_INVITES = "/$WORLD_ID/invite";
    private static final String PATH_WORLD_UNINVITE = "/$WORLD_ID/invite/$UUID";
    private static final String PATH_PENDING_INVITES_COUNT = "/count/pending";
    private static final String PATH_PENDING_INVITES = "/pending";
    private static final String PATH_ACCEPT_INVITE = "/accept/$INVITATION_ID";
    private static final String PATH_REJECT_INVITE = "/reject/$INVITATION_ID";
    private static final String PATH_UNINVITE_MYSELF = "/$WORLD_ID";
    private static final String PATH_WORLD_UPDATE = "/$WORLD_ID";
    private static final String PATH_SLOT_UPDATE = "/$WORLD_ID/slot";
    private static final String PATH_SLOT_SWITCH = "/$WORLD_ID/slot/$SLOT_ID";
    private static final String PATH_WORLD_OPEN = "/$WORLD_ID/open";
    private static final String PATH_WORLD_CLOSE = "/$WORLD_ID/close";
    private static final String PATH_WORLD_RESET = "/$WORLD_ID/reset";
    private static final String PATH_DELETE_WORLD = "/$WORLD_ID";
    private static final String PATH_WORLD_BACKUPS = "/$WORLD_ID/backups";
    private static final String PATH_WORLD_DOWNLOAD = "/$WORLD_ID/backups/download";
    private static final String PATH_WORLD_UPLOAD = "/$WORLD_ID/backups/upload";
    private static final String PATH_WORLD_UPLOAD_FINISHED = "/$WORLD_ID/backups/upload/finished";
    private static final String PATH_WORLD_UPLOAD_CANCELLED = "/$WORLD_ID/backups/upload/cancelled";
    private static final String PATH_CLIENT_COMPATIBLE = "/client/compatible";
    private static final String PATH_TOS_AGREED = "/tos/agreed";
    private static final String PATH_MCO_BUY = "/buy";
    private static final String PATH_STAGE_AVAILABLE = "/stageAvailable";
    private static Gson gson;
    
    public static RealmsClient createRealmsClient() {
        final String userName = Realms.userName();
        final String sessionId = Realms.sessionId();
        if (userName == null || sessionId == null) {
            return null;
        }
        return new RealmsClient(sessionId, userName, Realms.getProxy());
    }
    
    public static void switchToStage() {
        RealmsClient.baseUrl = "mcoapi-stage.minecraft.net";
    }
    
    public static void switchToProd() {
        RealmsClient.baseUrl = "mcoapi.minecraft.net";
    }
    
    public RealmsClient(final String sessionId, final String username, final Proxy proxy) {
        this.sessionId = sessionId;
        this.username = username;
        RealmsClientConfig.setProxy(proxy);
    }
    
    public RealmsServerList listWorlds() throws RealmsServiceException, IOException {
        return RealmsServerList.parse(this.execute(Request.get(this.url("worlds"))));
    }
    
    public RealmsServer getOwnWorld(final long n) throws RealmsServiceException, IOException {
        return RealmsServer.parse(this.execute(Request.get(this.url("worlds" + "/$ID".replace("$ID", String.valueOf(n))))));
    }
    
    public ServerActivityList getActivity(final long n) throws RealmsServiceException {
        return ServerActivityList.parse(this.execute(Request.get(this.url("activities" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(n))))));
    }
    
    public RealmsServerAddress join(final long n) throws RealmsServiceException, IOException {
        return RealmsServerAddress.parse(this.execute(Request.get(this.url("worlds" + "/$ID/join".replace("$ID", "" + n)), 5000, 30000)));
    }
    
    public void initializeWorld(final long n, final String s, final String s2) throws RealmsServiceException, IOException {
        this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf(n)), QueryBuilder.of("name", s).with("motd", s2).toQueryString()), "", 5000, 10000));
    }
    
    public Boolean mcoEnabled() throws RealmsServiceException, IOException {
        return Boolean.valueOf(this.execute(Request.get(this.url("mco/available"))));
    }
    
    public Boolean stageAvailable() throws RealmsServiceException, IOException {
        return Boolean.valueOf(this.execute(Request.get(this.url("mco/stageAvailable"))));
    }
    
    public CompatibleVersionResponse clientCompatible() throws RealmsServiceException, IOException {
        return CompatibleVersionResponse.valueOf(this.execute(Request.get(this.url("mco/client/compatible"))));
    }
    
    public void uninvite(final long n, final String s) throws RealmsServiceException {
        this.execute(Request.delete(this.url("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf(n)).replace("$UUID", s))));
    }
    
    public void uninviteMyselfFrom(final long n) throws RealmsServiceException {
        this.execute(Request.delete(this.url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(n)))));
    }
    
    public RealmsServer invite(final long n, final String s) throws RealmsServiceException, IOException {
        return RealmsServer.parse(this.execute(Request.put(this.url("invites" + "/$WORLD_ID/invite".replace("$WORLD_ID", String.valueOf(n)), QueryBuilder.of("profileName", s).toQueryString()), "")));
    }
    
    public BackupList backupsFor(final long n) throws RealmsServiceException {
        return BackupList.parse(this.execute(Request.get(this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(n))))));
    }
    
    public void update(final long n, final String s, final String s2) throws RealmsServiceException, UnsupportedEncodingException {
        QueryBuilder queryBuilder = QueryBuilder.of("name", s);
        if (s2 != null) {
            queryBuilder = queryBuilder.with("motd", s2);
        }
        this.execute(Request.put(this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(n)), queryBuilder.toQueryString()), ""));
    }
    
    public void updateSlot(final long n, final RealmsOptions realmsOptions) throws RealmsServiceException, UnsupportedEncodingException {
        this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/slot".replace("$WORLD_ID", String.valueOf(n)), QueryBuilder.of("options", realmsOptions.toJson()).toQueryString()), ""));
    }
    
    public boolean switchSlot(final long n, final int n2) throws RealmsServiceException {
        return Boolean.valueOf(this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(n)).replace("$SLOT_ID", String.valueOf(n2))), "")));
    }
    
    public void restoreWorld(final long n, final String s) throws RealmsServiceException {
        this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(n)), "backupId=" + s), "", 40000, 40000));
    }
    
    public WorldTemplateList fetchWorldTemplates() throws RealmsServiceException {
        return WorldTemplateList.parse(this.execute(Request.get(this.url("worlds/templates"))));
    }
    
    public WorldTemplateList fetchMinigames() throws RealmsServiceException {
        return WorldTemplateList.parse(this.execute(Request.get(this.url("worlds/minigames"))));
    }
    
    public Boolean putIntoMinigameMode(final long n, final String s) throws RealmsServiceException {
        return Boolean.valueOf(this.execute(Request.put(this.url("worlds" + "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", s).replace("$WORLD_ID", String.valueOf(n))), "")));
    }
    
    public Ops op(final long n, final String s) throws RealmsServiceException {
        return Ops.parse(this.execute(Request.post(this.url("ops" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(n)), QueryBuilder.of("profileName", s).toQueryString()), "")));
    }
    
    public Ops deop(final long n, final String s) throws RealmsServiceException {
        return Ops.parse(this.execute(Request.delete(this.url("ops" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(n)), QueryBuilder.of("profileName", s).toQueryString()))));
    }
    
    public Boolean open(final long n) throws RealmsServiceException, IOException {
        return Boolean.valueOf(this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(n))), "")));
    }
    
    public Boolean close(final long n) throws RealmsServiceException, IOException {
        return Boolean.valueOf(this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(n))), "")));
    }
    
    public Boolean resetWorldWithSeed(final long n, final String s, final Integer n2, final boolean b) throws RealmsServiceException, IOException {
        QueryBuilder queryBuilder = QueryBuilder.empty();
        if (s != null && s.length() > 0) {
            queryBuilder = queryBuilder.with("seed", s);
        }
        return Boolean.valueOf(this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(n)), queryBuilder.with("levelType", n2).with("generateStructures", b).toQueryString()), "", 30000, 80000)));
    }
    
    public Boolean resetWorldWithTemplate(final long n, final String s) throws RealmsServiceException, IOException {
        QueryBuilder queryBuilder = QueryBuilder.empty();
        if (s != null) {
            queryBuilder = queryBuilder.with("template", s);
        }
        return Boolean.valueOf(this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(n)), queryBuilder.toQueryString()), "", 30000, 80000)));
    }
    
    public Subscription subscriptionFor(final long n) throws RealmsServiceException, IOException {
        return Subscription.parse(this.execute(Request.get(this.url("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(n))))));
    }
    
    public int pendingInvitesCount() throws RealmsServiceException {
        return Integer.parseInt(this.execute(Request.get(this.url("invites/count/pending"))));
    }
    
    public PendingInvitesList pendingInvites() throws RealmsServiceException {
        return PendingInvitesList.parse(this.execute(Request.get(this.url("invites/pending"))));
    }
    
    public void acceptInvitation(final String s) throws RealmsServiceException {
        this.execute(Request.put(this.url("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", s)), ""));
    }
    
    public RealmsState fetchRealmsState() throws RealmsServiceException {
        return RealmsState.parse(this.execute(Request.get(this.url("mco/buy"))));
    }
    
    public String download(final long n) throws RealmsServiceException {
        return this.execute(Request.get(this.url("worlds" + "/$WORLD_ID/backups/download".replace("$WORLD_ID", String.valueOf(n)))));
    }
    
    public UploadInfo upload(final long n, final String token) throws RealmsServiceException {
        final String url = this.url("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf(n)));
        final UploadInfo uploadInfo = new UploadInfo();
        if (token != null) {
            uploadInfo.setToken(token);
        }
        return UploadInfo.parse(this.execute(Request.put(url, RealmsClient.gson.toJson(uploadInfo))));
    }
    
    public void uploadCancelled(final long n, final String token) throws RealmsServiceException {
        final String url = this.url("worlds" + "/$WORLD_ID/backups/upload/cancelled".replace("$WORLD_ID", String.valueOf(n)));
        final UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.setToken(token);
        this.execute(Request.put(url, RealmsClient.gson.toJson(uploadInfo)));
    }
    
    public void uploadFinished(final long n) throws RealmsServiceException {
        this.execute(Request.put(this.url("worlds" + "/$WORLD_ID/backups/upload/finished".replace("$WORLD_ID", String.valueOf(n))), ""));
    }
    
    public void rejectInvitation(final String s) throws RealmsServiceException {
        this.execute(Request.put(this.url("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", s)), ""));
    }
    
    public void agreeToTos() throws RealmsServiceException {
        this.execute(Request.post(this.url("mco/tos/agreed"), ""));
    }
    
    public void sendPingResults(final PingResult pingResult) throws RealmsServiceException {
        this.execute(Request.post(this.url("regions/ping/stat"), RealmsClient.gson.toJson(pingResult)));
    }
    
    public Boolean trialAvailable() throws RealmsServiceException, IOException {
        return Boolean.valueOf(this.execute(Request.get(this.url("trial"))));
    }
    
    public Boolean createTrial() throws RealmsServiceException, IOException {
        return Boolean.valueOf(this.execute(Request.put(this.url("trial"), "")));
    }
    
    public void deleteWorld(final long n) throws RealmsServiceException, IOException {
        this.execute(Request.delete(this.url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(n)))));
    }
    
    private String url(final String s) {
        return this.url(s, null);
    }
    
    private String url(final String s, final String s2) {
        return new URI("https", RealmsClient.baseUrl, "/" + s, s2, null).toASCIIString();
    }
    
    private String execute(final Request request) throws RealmsServiceException {
        request.cookie("sid", this.sessionId);
        request.cookie("user", this.username);
        request.cookie("version", RealmsSharedConstants.VERSION_STRING);
        final String version = RealmsVersion.getVersion();
        if (version != null) {
            request.cookie("realms_version", version);
        }
        final int responseCode = request.responseCode();
        if (responseCode == 503) {
            throw new RetryCallException(request.getRetryAfterHeader());
        }
        final String text = request.text();
        if (responseCode >= 200 && responseCode < 300) {
            return text;
        }
        if (responseCode == 401) {
            final String header = request.getHeader("WWW-Authenticate");
            RealmsClient.LOGGER.info("Could not authorize you against Realms server: " + header);
            throw new RealmsServiceException(responseCode, header, -1, header);
        }
        if (text == null || text.length() == 0) {
            RealmsClient.LOGGER.error("Realms error code: " + responseCode + " message: " + text);
            throw new RealmsServiceException(responseCode, text, responseCode, "");
        }
        final RealmsError realmsError = new RealmsError(text);
        RealmsClient.LOGGER.error("Realms http code: " + responseCode + " -  error code: " + realmsError.getErrorCode() + " -  message: " + realmsError.getErrorMessage());
        throw new RealmsServiceException(responseCode, text, realmsError);
    }
    
    static {
        LOGGER = LogManager.getLogger();
        RealmsClient.baseUrl = "mcoapi.minecraft.net";
        RealmsClient.gson = new Gson();
    }
    
    public enum CompatibleVersionResponse
    {
        COMPATIBLE("COMPATIBLE", 0), 
        OUTDATED("OUTDATED", 1), 
        OTHER("OTHER", 2);
        
        private static final CompatibleVersionResponse[] $VALUES;
        
        private CompatibleVersionResponse(final String s, final int n) {
        }
        
        static {
            $VALUES = new CompatibleVersionResponse[] { CompatibleVersionResponse.COMPATIBLE, CompatibleVersionResponse.OUTDATED, CompatibleVersionResponse.OTHER };
        }
    }
}
