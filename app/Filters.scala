import filters.ContentSecurityPolicyFilter

import com.google.inject.Inject

import play.api.http.DefaultHttpFilters

class Filters @Inject() (
  contentSecurityPolicyFilter: ContentSecurityPolicyFilter,
) extends DefaultHttpFilters(contentSecurityPolicyFilter)